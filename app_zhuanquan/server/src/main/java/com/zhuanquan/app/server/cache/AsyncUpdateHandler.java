package com.zhuanquan.app.server.cache;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.constants.AsyncUpdateType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.dal.dao.BaseDao;

/**
 * 
 * @author zhangjun
 *
 */
public abstract class  AsyncUpdateHandler<T> extends BaseDao {
	
	private static final String STAT_ENABLE = "1";
	
	private static final String STAT_DISABLE = "0";

	private static final int DEFAULT_TIMES_LIMIT = 4;
	
	@Resource
	private RedisHelper redisHelper;
	
	/**
	 * 获取当前的异步更新的类型
	 * @return
	 */
	abstract  AsyncUpdateType getAsyncUpdateType();
	
	/**
	 * 查询记录,业务不允许直接调用这个方法
	 * 
	 * @return
	 */
	abstract protected boolean queryIsEnableFromDB(long uid,long targetId);
	
	
	/**
	 * 实现入库操作，点赞入库，收藏入库等
	 * @param uid
	 * @param targetId
	 * @param isEnable
	 */
	abstract protected void doEnableOrDisableToDb(long uid,long targetId,boolean isEnable);
	
	
	/**
	 * 获取 目标id 总数增长或者减少的缓存key，比如 作品点赞数，收藏数，关注数等
	 */
	protected String getTargetIdIncreaseOrDecreaseKey(long targetId) {
		
		AsyncUpdateType type = getAndCheckAsyncUpdateType();

		
		return RedisKeyBuilder.getTargetIdTotalNumKey(type,targetId);
	}
	
	
	/**
	 * 获取用户对应关注，点赞，评论的key，这个key里 uid是主体维度
	 * 
	 * @param uid
	 * @return
	 */
	protected String getIsEnableKey(long uid) {
		
		
		AsyncUpdateType type = getAndCheckAsyncUpdateType();

		
		return RedisKeyBuilder.getIsEnableKeyByUidWithHash(type,uid);
	}
	
	
	private AsyncUpdateType  getAndCheckAsyncUpdateType(){
		
		AsyncUpdateType type = getAsyncUpdateType();

		//确保不为null
		Asserts.notNull(type, "AsyncUpdateType");
		
		return type;
	}
	
	
	/**
	 * 获取操作太频繁，做控制的key，比如点赞太频繁，收藏太频繁
	 * @param uid
	 * @param targetId
	 * @return
	 */
	protected String getOperTooManyTimesLockKey(long uid, long targetId) {
		
		AsyncUpdateType type = getAndCheckAsyncUpdateType();
		
		return RedisKeyBuilder.getOperTooManyTimesLockKey(type,uid,targetId);
		

	}

	/**
	 * 操作太频繁的次数限制
	 * @return
	 */
	protected int getOperTooManyLimitTimes() {
		return DEFAULT_TIMES_LIMIT;
	}
	
	
	
	/**
	 * 查询是否是enable状态
	 * 
	 * @param uid
	 * @param targetId
	 * @return
	 */
	protected boolean queryIsEnableByCache(long uid,long targetId) {
		
		String key = getIsEnableKey(uid);
		
		String hashKey = targetId+"";
		
		String obj = redisHelper.hashGet(key, hashKey);

		if (obj != null) {
			return obj.equals(STAT_ENABLE);
		}
		
		
		boolean isEnable = queryIsEnableFromDB(uid, targetId);
		redisHelper.hashSet(key,hashKey, isEnable?STAT_ENABLE:STAT_DISABLE);
		redisHelper.expire(key, 3, TimeUnit.MINUTES);
		return isEnable;		
	}
	
	
	
	/**
	 * 启动或者停用， 比如点赞/取消点赞，收藏/取消收藏，不保证强一致性
	 * 
	 * @param uid
	 * @param targetId
	 * @param key
	 * @param hashKey
	 */
	protected void enableOrDisable(long uid, long targetId,boolean isEnable) {
		
		//检查是否操作太频繁
		checkIsLimited(uid, targetId);
		
		
		boolean enableStat = queryIsEnableByCache(uid, targetId);
		
		//缓存状态和期望的状态一致，忽略
		if(enableStat == isEnable) {
			return;
		}
		

		AsyncUpdateType type = getAndCheckAsyncUpdateType();
		
		//操作入库
		doEnableOrDisableToDb(uid, targetId, isEnable);
		
		//删除缓存中的对应的值，
		redisHelper.hashDelete(getIsEnableKey(uid), targetId+"");

		//增加或者减少关注数，点赞数，评论数等,不保证强一致性
		String targetChangeKey = getTargetIdIncreaseOrDecreaseKey(targetId);
		redisHelper.increase(targetChangeKey, isEnable?1:-1);
		redisHelper.expire(targetChangeKey, 3, TimeUnit.MINUTES);
		
		//异步更新的事件，异步更新点赞数，收藏数，评论数,关注数等等
		//先在缓存里加上这个，需要更新
		String asyncKey = RedisKeyBuilder.getAsyncUpateTotalNumZSetKey(type);
		redisHelper.zsetIncrby(asyncKey, 1.00, targetId+"");

	}
	
	

	
	
	/**
	 * 检查是否要限制
	 * @param uid
	 * @param targetId
	 */
	private void checkIsLimited(long uid, long targetId) {
		
		String limitKey = getOperTooManyTimesLockKey(uid, targetId);
		
		//key不存在，不需要做校验，限制操作频繁
		if(StringUtils.isEmpty(limitKey)) {
			return;
		}
		
		String value = redisHelper.valueGet(limitKey);

		int times = getOperTooManyLimitTimes();
		
		//至少大于1
		if(times<=1) {
			times = DEFAULT_TIMES_LIMIT;
		}
		
		if (value != null && Integer.parseInt(value) > times) {
			throw new BizException(BizErrorCode.EX_NOT_ALLOW_FREQUENT_OPER.getCode());
		} else {
			redisHelper.increase(limitKey, 1);
			redisHelper.expire(limitKey, 2, TimeUnit.MINUTES);
		}
		
	}
	

	
}