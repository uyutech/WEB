package com.zhuanquan.app.server.cache;

import com.zhuanquan.app.common.model.user.UserFavourite;

/**
 * 用户收藏的缓存
 * @author zhangjun
 *
 */
public interface UserFavCache {
	
	/**
	 * 查询记录，使用缓存
	 * @param uid
	 * @param workId
	 * @return
	 */
	UserFavourite queryUserFavouriteRecord(long uid, long workId);
	
}