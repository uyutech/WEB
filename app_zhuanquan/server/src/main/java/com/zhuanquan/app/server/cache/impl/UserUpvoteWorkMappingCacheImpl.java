package com.zhuanquan.app.server.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.GracefulRedisTemplate;
import com.zhuanquan.app.common.component.cache.redis.RedisSetOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisZSetOperations;
import com.zhuanquan.app.common.component.cache.redis.lock.RedisSimpleLock;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.Works;
import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;
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

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	private GracefulRedisTemplate<String, String> gracefulRedisTemplate;

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

		// 有变动的workid
		String changedWorkIdsKey = RedisKeyBuilder.getUpvoteWorkChangedWorkIdsKey();
		redisSetOperations.add(changedWorkIdsKey, workId + "");
		redisHelper.expire(changedWorkIdsKey, 6, TimeUnit.HOURS);

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
		redisHelper.expire(upvoteWorkKey, 30, TimeUnit.MINUTES);

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

		if (obj != null) {
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
		if (obj != null) {
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

		if (obj != null) {
			return obj.equals(UserUpvoteWorkMapping.STAT_ENABLE + "");
		}

		UserUpvoteWorkMapping record = userUpvoteWorkMappingDAO.queryUserUpvoteWorkMapping(uid, workId);

		// 是否已点赞
		boolean isUpvote = (record != null && record.getStatus() == UserUpvoteWorkMapping.STAT_ENABLE);

		redisHelper.hashSet(upvoteWorkKey, workId + "", record.getStatus() + "");
		redisHelper.expire(upvoteWorkKey, 30, TimeUnit.MINUTES);

		return isUpvote;
	}

	@Override
	public void doPersistUpvoteNumTask() {

		String jobLockKey = RedisKeyBuilder.getExecUpvoteTaskLock();

		boolean isLock = redisSimpleLock.tryLock(jobLockKey, 5, TimeUnit.MINUTES);

		if (!isLock) {
			logger.info("begin to doPersistUpvoteNumTask,but can not get lock,ignore this ****************************");

			return;
		}

		try {
			// 处理个人的点赞记录
			persistUidUpvoteWorkRecord();

			// 作品点赞总数更新
			persistWorkUpvoteTotalNumRecord();
			
		} finally {

			try {
				redisSimpleLock.releaseLock(jobLockKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 持久化变动的用户点赞记录到数据库
	 */
	private void persistUidUpvoteWorkRecord() {

		logger.info("begin to persistUidUpvoteWorkRecord------------->");

		// 不管什么样，都需要加到变动列表里，因为有可能
		String upvoteChangedUidsKey = RedisKeyBuilder.getUpvoteWorkChangedUidsKey();

		String dstKey = RedisKeyBuilder.getUpvoteWorkChangedDstUidsKey();

		// 看原来的upvoteChangedUidsKey理有哪些变动的
		Set<String> keys = redisSetOperations.members(upvoteChangedUidsKey);

		if (keys == null || keys.size() == 0) {
			logger.info("end to persistUidUpvoteWorkRecord,nothing need to process------------->");

			return;
		}

		// 变动的迁移到另外个key理
		gracefulRedisTemplate.batchMove(upvoteChangedUidsKey, keys, dstKey);

		logger.info("process persistUidUpvoteWorkRecord : batchMove finished!");
		// 变动的key
		Set<String> changedUids = redisSetOperations.members(dstKey);

		logger.info("process persistUidUpvoteWorkRecord : changedUids =" + JSON.toJSONString(changedUids));

		// 批次号，每个批次最多同步200个uid变动信息
		int batchNum = 0;

		int maxBatchNum = 200;

		List<String> uidTempList = new ArrayList<String>();

		// 插入的列表
		List<UserUpvoteWorkMapping> insertList = new ArrayList<UserUpvoteWorkMapping>();

		// 更新的列表
		List<UserUpvoteWorkMapping> updateList = new ArrayList<UserUpvoteWorkMapping>();

		for (String uid : changedUids) {

			uidTempList.add(uid);
			// 处理单个用户id下的所有的记录，放到insert或者update队列
			determineToInsertOrUpdate(Long.parseLong(uid), insertList, updateList);

			logger.info("process persistUidUpvoteWorkRecord :uid=" + uid + " has processed end!");

			// 一批次达到200个，提交一次
			if (uidTempList.size() >= maxBatchNum) {
				// 批量插入
				userUpvoteWorkMappingDAO.insertBatchUserUpvoteWorkMapping(insertList);
				insertList.clear();

				// 批量更新
				userUpvoteWorkMappingDAO.updateBatchUserUpvoteWorkMapping(updateList);
				updateList.clear();

				logger.info("real process persistUidUpvoteWorkRecord :[batchNum]=" + batchNum + ",[uids]="
						+ JSON.toJSONString(uidTempList));

				//提交了一批，就删掉dstkey里的值
				redisSetOperations.remove(dstKey, uidTempList);
				
				// uid清空
				uidTempList.clear();

				batchNum++;

			}

		}

		if (uidTempList.size() > 0) {
			userUpvoteWorkMappingDAO.insertBatchUserUpvoteWorkMapping(insertList);
			userUpvoteWorkMappingDAO.updateBatchUserUpvoteWorkMapping(updateList);

			//提交了一批，就删掉dstkey里的值
			redisSetOperations.remove(dstKey, uidTempList);
			uidTempList.clear();
			
			logger.info("real process persistUidUpvoteWorkRecord :[batchNum]=" + batchNum + ",[uids]="
					+ JSON.toJSONString(uidTempList));

		}

		logger.info("end to persistWorkUpvoteTotalNumRecord, job exec finished------------->");

	}

	/**
	 * 决定是insert还是update
	 * 
	 * @param uid
	 * @param insertList
	 * @param updateList
	 */
	private void determineToInsertOrUpdate(long uid, List<UserUpvoteWorkMapping> insertList,
			List<UserUpvoteWorkMapping> updateList) {

		String upvoteWorkKey = RedisKeyBuilder.getUserUpvoteWorkKey(uid);

		Map<String, String> map = redisHelper.hashGetByKey(upvoteWorkKey);

		if (MapUtils.isEmpty(map)) {
			return;
		}

		// 哪些是已经插入的
		List<Long> hasInsertIds = userUpvoteWorkMappingDAO.queryHasInsertWorkIds(uid, map.keySet());

		for (Entry<String, String> entry : map.entrySet()) {
			UserUpvoteWorkMapping record = new UserUpvoteWorkMapping();
			record.setStatus(Integer.parseInt(entry.getValue()));
			record.setUid(uid);
			record.setWorkId(Long.parseLong(entry.getKey()));

			if (hasInsertIds.indexOf(Long.parseLong(entry.getKey())) != -1) {
				insertList.add(record);
			} else {
				updateList.add(record);
			}
		}

	}

	/**
	 * 
	 * 持久化 作品被点赞的总数到数据库
	 */
	private void persistWorkUpvoteTotalNumRecord() {

		logger.info("begin to persistWorkUpvoteTotalNumRecord------------->");

		// 点赞数有变动的workid
		String changedWorkIdsKey = RedisKeyBuilder.getUpvoteWorkChangedWorkIdsKey();

		Set<String> keys = redisSetOperations.members(changedWorkIdsKey);

		if (keys == null || keys.size() == 0) {
			logger.info("end to persistWorkUpvoteTotalNumRecord,nothing need to process------------->");
			return;
		}

		String dstKey = RedisKeyBuilder.getUpvoteWorkChangedWorkIdsDstKey();

		// 变动的迁移到另外个key里
		gracefulRedisTemplate.batchMove(changedWorkIdsKey, keys, dstKey);

		logger.info("process persistWorkUpvoteTotalNumRecord : batchMove finished!");
		// 变动的key
		Set<String> changedWorkIds = redisSetOperations.members(dstKey);

		logger.info("process persistWorkUpvoteTotalNumRecord : changedUids =" + JSON.toJSONString(changedWorkIds));

		// 批次号
		int batchNum = 0;
		// 批量插入500最大
		int oneBatchMaxNum = 500;

		List<BatchUpdateWorkUpvoteNumBo> updateList = new ArrayList<BatchUpdateWorkUpvoteNumBo>();

		// 遍历
		for (String workId : changedWorkIds) {

			String workUpvoteTotalNumKey = RedisKeyBuilder.getWorkUpvoteTotalNumKey(workId);

			Double obj = redisZSetOperations.score(workUpvoteTotalNumKey, workId);

			if (obj == null) {

				logger.info("process persistWorkUpvoteTotalNumRecord : [workId]=" + workId
						+ " process finished,not need to process!");

				continue;
			}

			logger.info("process persistWorkUpvoteTotalNumRecord : [workId]=" + workId + " process finished!");

			BatchUpdateWorkUpvoteNumBo bo = new BatchUpdateWorkUpvoteNumBo();

			bo.setUpvoteNum(Math.round(obj));
			bo.setWorkId(Long.parseLong(workId));

			updateList.add(bo);

			// 达到500个提交一次
			if (updateList.size() >= oneBatchMaxNum) {
				// 批量更新点赞数
				worksDAO.updateBatchForWorkUpvoteNum(updateList);

				
				redisSetOperations.remove(dstKey, uidTempList);

				
				logger.info("real process persistWorkUpvoteTotalNumRecord : [batchNum]=" + batchNum + ",[updateList]="
						+ JSON.toJSONString(updateList));

				updateList.clear();

				batchNum++;
			}

		}

		if (updateList.size() > 0) {
			// 批量更新点赞数
			worksDAO.updateBatchForWorkUpvoteNum(updateList);

			logger.info("real process persistWorkUpvoteTotalNumRecord : [batchNum]=" + batchNum + ",[updateList]="
					+ JSON.toJSONString(updateList));

		}

		logger.info("end to persistWorkUpvoteTotalNumRecord, job exec finished------------->");

	}

}