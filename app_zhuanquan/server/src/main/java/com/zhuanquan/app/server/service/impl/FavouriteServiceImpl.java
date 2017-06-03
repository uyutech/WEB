package com.zhuanquan.app.server.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.user.UserFavourite;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.dal.dao.user.UserFavouriteDAO;
import com.zhuanquan.app.dal.dao.user.UserFavouriteGroupDAO;
import com.zhuanquan.app.server.cache.WorksCache;
import com.zhuanquan.app.server.service.FavouriteService;

@Service
public class FavouriteServiceImpl implements FavouriteService {

	private static final int TIMES_LIMIT = 3;

	
	@Resource
	private UserFavouriteDAO userFavouriteDAO;

	@Resource
	private UserFavouriteGroupDAO userFavouriteGroupDAO;

	@Resource
	private WorksCache worksCache;
	
	@Resource
	private RedisHelper redisHelper;

	@Override
	public void favouriteWork(long uid, long workId) {

		//操作过于频繁的控制，2分钟内不允许对同一个连续收藏3次
		//
		String key = RedisKeyBuilder.favTooManyTimesLock(uid, workId);
		
		String value = redisHelper.valueGet(key);
		
		if(value!=null && Integer.parseInt(value)>TIMES_LIMIT) {
			throw new BizException(BizErrorCode.EX_NOT_ALLOW_FREQUENT_OPER.getCode());
		} else {
			redisHelper.increase(key, 1);
			redisHelper.expire(key, 2, TimeUnit.MINUTES);
		}
		
		
		doFavOrCancel(uid, workId, true);
	}

	@Override
	public void cancelFavouriteWork(long uid, long workId) {
		doFavOrCancel(uid, workId, false);
	}

	/**
	 * 收藏或者取消收藏
	 * 
	 * @param uid
	 * @param workId
	 *            作品id
	 * @param isFav
	 *            true-收藏 false-取消收藏
	 */
	private void doFavOrCancel(long uid, long workId, boolean isFav) {

		WorkBase obj = worksCache.queryWorkById(workId);

		if (obj == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		
		//如果是取消收藏的
		if(!isFav) {
			userFavouriteDAO.updateUserFavouriteRecordStat(uid, workId, UserFavourite.STAT_DIS_FAV);
			
			return;
		} 
		
		
		UserFavourite record = UserFavourite.createDefaultGroupRecord(uid, workId);

		userFavouriteDAO.insertOrUpdateUserFavRecord(record);

	}

	@Override
	public List<Long> queryAllFavouriteWorks(long uid) {
		
		return userFavouriteDAO.queryAllFavWorkIds(uid);

	}

	@Override
	public boolean hasFavouriteWork(long uid, long workId) {
		
		UserFavourite record = userFavouriteDAO.queryUserFavouriteRecord(uid, workId);

		if(record == null) {
			return false;
		}
		
		return record.getStatus() == UserFavourite.STAT_FAV ? true:false;
	}

}