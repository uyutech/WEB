package com.zhuanquan.app.server.cache.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.RedisSetOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisZSetOperations;
import com.zhuanquan.app.common.component.cache.redis.lock.RedisSimpleLock;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.Works;
import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;
import com.zhuanquan.app.dal.dao.author.WorksDAO;
import com.zhuanquan.app.dal.dao.user.UserUpvoteWorkMappingDAO;
import com.zhuanquan.app.server.cache.UserUpvoteWorkMappingCache;

/**
 * 
 * @author zhangjun
 *
 */
@Service
public class UserUpvoteWorkMappingCacheImpl implements UserUpvoteWorkMappingCache {

	@Resource
	private WorksDAO worksDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private RedisSimpleLock redisSimpleLock;

	@Resource
	private RedisSetOperations<String, String> redisSetOperations;

	@Resource
	private RedisZSetOperations<String, String> redisZSetOperations;

	@Resource
	private UserUpvoteWorkMappingDAO userUpvoteWorkMappingDAO;

	@Override
	public void upvoteWork(long uid, long workId) {

		addOrCancelUpvote(uid, workId, true);

	}

	/**
	 * 点赞或者取消点赞
	 * 
	 * @param uid
	 * @param workId
	 * @param isAddOrReduce
	 *            true点赞 false 取消点赞
	 */
	private void addOrCancelUpvote(long uid, long workId, boolean isAddOrReduce) {

		String lockKey = RedisKeyBuilder.getUserUpvoteWorkLockKey(uid, workId);

		boolean isLocked = redisSimpleLock.tryLock(lockKey);

		// 获取不到锁直接报错，不允许并发操作
		if (!isLocked) {
			throw new BizException(BizErrorCode.EX_NOT_ALLOW_CONCURRENT_OPER.getCode());
		}

		try {
			doUpvoteWork(uid, workId, isAddOrReduce);

		} finally {
			// 释放分布式锁
			redisSimpleLock.releaseLock(lockKey);
		}

	}

	/**
	 * 执行 点赞/取消点赞操作
	 * 
	 * @param uid
	 * @param workId
	 * @param addOrCancel
	 *            点赞或者取消点赞
	 */
	private void doUpvoteWork(long uid, long workId, boolean addOrCancel) {

		// 不管什么样，都需要加到变动列表里，因为有可能
		String upvoteChangedUidsKey = RedisKeyBuilder.getUpvoteWorkChangedUidsKey();
		// 变动的uid加到列表里
		redisSetOperations.add(upvoteChangedUidsKey, uid + "");
		redisHelper.expire(upvoteChangedUidsKey, 6, TimeUnit.HOURS);

		String upvoteWorkKey = RedisKeyBuilder.getUserUpvoteWorkKey(uid);

		String obj = redisHelper.hashGet(upvoteWorkKey, workId + "");

		int targetState = addOrCancel ? UserUpvoteWorkMapping.STAT_ENABLE : UserUpvoteWorkMapping.STAT_DISABLE;

		if (obj != null) {

			// 已经 处于点赞状态了，不允许继续点赞
			if (obj.equals(targetState + "")) {
				throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
			}

			// 设置为点赞状态,1个小时失效
			redisHelper.hashSet(upvoteWorkKey, workId + "", targetState + "");
			redisHelper.expire(upvoteWorkKey, 1, TimeUnit.HOURS);

			// 这里点赞的数据不直接入库，等待后面定时任务批量入库

			// 点赞总数增加。
			addOrReduceWorkUpvoteNum(workId, addOrCancel ? 1 : -1);

			return;
		}

		// 检查workid是否存在
		Works work = worksDAO.queryWorkById(workId);

		if (work == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		UserUpvoteWorkMapping record = userUpvoteWorkMappingDAO.queryUserUpvoteWorkMapping(uid, workId);

		// 是否已点赞
		boolean isUpvote = (record != null && record.getStatus() == UserUpvoteWorkMapping.STAT_ENABLE);

		// 设置为点赞状态,1个小时失效，无论数据库实际是点赞/取消点赞，最终缓存的状态都是和预期的targetstate一致
		redisHelper.hashSet(upvoteWorkKey, workId + "", targetState + "");
		redisHelper.expire(upvoteWorkKey, 1, TimeUnit.HOURS);

		// 当前的数据库的状态和期望状态一样，抛出异常，比如数据库是已点赞，当前也是点赞操作，会抛出异常
		if (isUpvote == addOrCancel) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		// 点赞数加1
		addOrReduceWorkUpvoteNum(workId, addOrCancel ? 1 : -1);

		return;
	}

	/**
	 * 增加/减少作品的点赞数，不保证强一致性
	 * 
	 * @param workId
	 * @param num
	 *            可正数，可负数
	 */
	private void addOrReduceWorkUpvoteNum(long workId, int num) {

		String workUpvoteTotalNumKey = RedisKeyBuilder.getWorkUpvoteTotalNumKey(workId);

		Double obj = redisZSetOperations.score(workUpvoteTotalNumKey, workId);

		if (obj != null) {
			// 点赞数加1
			redisZSetOperations.incrementScore(workUpvoteTotalNumKey, workId + "", num);
			// 超时时间24小时
			redisHelper.expire(workUpvoteTotalNumKey, 24, TimeUnit.HOURS);

			return;
		}

		// 检查workid是否存在
		Works work = worksDAO.queryWorkById(workId);

		if (work == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		// 从数据查询回来之后，再查一把，防止并发更新了导致缓存值错误
		obj = redisZSetOperations.score(workUpvoteTotalNumKey, workId);

		// obj 为null,初始化一下,尽管还是有可能重复操作的，但是几率小很多
		if (obj == null) {

			redisZSetOperations.add(workUpvoteTotalNumKey, workId + "", work.getUpvoteNum() + num);
			// 超时时间24小时
			redisHelper.expire(workUpvoteTotalNumKey, 24, TimeUnit.HOURS);

		} else {

			// 点赞数加 或者减
			redisZSetOperations.incrementScore(workUpvoteTotalNumKey, workId + "", num);
			// 超时时间24小时
			redisHelper.expire(workUpvoteTotalNumKey, 24, TimeUnit.HOURS);
		}

	}

	@Override
	public void cancelUpvoteWork(long uid, long workId) {

		addOrCancelUpvote(uid, workId, false);
	}

	@Override
	public long queryWorkUpvoteNum(long workId) {
		
		String workUpvoteTotalNumKey = RedisKeyBuilder.getWorkUpvoteTotalNumKey(workId);

		Double obj = redisZSetOperations.score(workUpvoteTotalNumKey, workId);
		
		if(obj!=null) {
			return Math.round(obj);
		}
		
		// 检查workid是否存在
		Works work = worksDAO.queryWorkById(workId);

		if (work == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}
		
		// 从数据查询回来之后，再查一把，防止并发更新了导致缓存值错误
		obj = redisZSetOperations.score(workUpvoteTotalNumKey, workId);
		
		//
		if(obj!=null) {
			return Math.round(obj);
		} else {

			redisZSetOperations.add(workUpvoteTotalNumKey, workId + "", work.getUpvoteNum());
			// 超时时间24小时
			redisHelper.expire(workUpvoteTotalNumKey, 24, TimeUnit.HOURS);
			return work.getUpvoteNum();
		}
		

	}

	@Override
	public boolean hasUpvoteWork(long uid, long workId) {
		
		String upvoteWorkKey = RedisKeyBuilder.getUserUpvoteWorkKey(uid);

		String obj = redisHelper.hashGet(upvoteWorkKey, workId + "");
		
		if(obj!=null) {
			return obj.equals(UserUpvoteWorkMapping.STAT_ENABLE+"");
		}
		

		UserUpvoteWorkMapping record = userUpvoteWorkMappingDAO.queryUserUpvoteWorkMapping(uid, workId);

		// 是否已点赞
		boolean isUpvote = (record != null && record.getStatus() == UserUpvoteWorkMapping.STAT_ENABLE);

		redisHelper.hashSet(upvoteWorkKey, workId + "", record.getStatus()+"");
		redisHelper.expire(upvoteWorkKey, 1, TimeUnit.HOURS);
		
		return isUpvote;
	}

}