package com.zhuanquan.app.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.Works;
import com.zhuanquan.app.common.model.user.UserFavourite;
import com.zhuanquan.app.dal.dao.user.UserFavouriteDAO;
import com.zhuanquan.app.dal.dao.user.UserFavouriteGroupDAO;
import com.zhuanquan.app.server.cache.WorksCache;
import com.zhuanquan.app.server.service.FavouriteService;

@Service
public class FavouriteServiceImpl implements FavouriteService {

	@Resource
	private UserFavouriteDAO userFavouriteDAO;

	@Resource
	private UserFavouriteGroupDAO userFavouriteGroupDAO;

	@Resource
	private WorksCache worksCache;

	@Override
	public void favouriteWork(long uid, long workId) {

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

		Works obj = worksCache.queryWorkById(workId);

		if (obj == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		UserFavourite record = userFavouriteDAO.queryUserFavouriteRecord(uid, workId);

		// 原记录不存在
		if (record == null) {

			// 原纪录不存在的化，不支持取消收藏
			if (!isFav) {
				throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
			}

			record = UserFavourite.createDefaultGroupRecord(uid, workId);

			userFavouriteDAO.insertUserFavRecord(record);

			return;
			
		} else {

			// 原纪录存在
			int targetStat = isFav ? UserFavourite.STAT_FAV : UserFavourite.STAT_DIS_FAV;

			if (record.getStatus() == targetStat) {
				throw new BizException(BizErrorCode.EX_FAV_ILLEGLE_FAV_STATUS.getCode());
			}

			// 更新到预期到状态
			userFavouriteDAO.updateUserFavouriteRecord(uid, workId, targetStat);

		}
	}

	@Override
	public List<Long> queryAllFavouriteWorks(long uid) {
		
		List<UserFavourite> list = userFavouriteDAO.queryAllFavWork(uid);
		

		return null;
	}

	@Override
	public boolean hasFavouriteWork(long uid, long workId) {
		
		UserFavourite record = userFavouriteDAO.queryUserFavouriteRecord(uid, workId);

		if(record == null) {
			return false;
		}
		
		return record.getStatus() == UserFavourite.STAT_FAV?true:false;
	}

}