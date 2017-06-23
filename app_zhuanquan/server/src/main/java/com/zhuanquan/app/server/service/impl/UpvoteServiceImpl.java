package com.zhuanquan.app.server.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.server.cache.UserUpvoteWorkMappingCache;
import com.zhuanquan.app.server.service.UpvoteService;

@Service
public class UpvoteServiceImpl implements UpvoteService {

	private static final int TIMES_LIMIT = 3;
	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private UserUpvoteWorkMappingCache userUpvoteWorkMappingCache;
	
	@Override
	public void upvoteWork(long uid, long workId) {
		
		//操作过于频繁的控制，2分钟内不允许对同一个连续点赞3次
		//
		checkIsLimited(uid, workId);
		
		userUpvoteWorkMappingCache.upvoteWork(uid, workId);
		
	}

	@Override
	public void cancelUpvoteWork(long uid, long workId) {
		
		checkIsLimited(uid, workId);
		userUpvoteWorkMappingCache.cancelUpvoteWork(uid, workId);
		
	}

	@Override
	public long queryWorkUpvoteNum(long workId) {
		
		
		//TODO need implement
		return 0;
	}

	@Override
	public boolean hasUpvoteWork(long uid, long workId) {

		return userUpvoteWorkMappingCache.hasUpvoteWork(uid, workId);
	}
	
	

	/**
	 * 
	 * @param uid
	 * @param authorId
	 */
	private void checkIsLimited(long uid,long workId) {
		
		//操作过于频繁的控制，2分钟内不允许对同一个连续超过 6次
		//
		String key = RedisKeyBuilder.upvoteTooManyTimesLock(uid, workId);
		
		String value = redisHelper.valueGet(key);
		
		if(value!=null && Integer.parseInt(value)>TIMES_LIMIT) {
			throw new BizException(BizErrorCode.EX_NOT_ALLOW_FREQUENT_OPER.getCode());
		} else {
			redisHelper.increase(key, 1);
			redisHelper.expire(key, 2, TimeUnit.MINUTES);
		}
		
	}
	
}