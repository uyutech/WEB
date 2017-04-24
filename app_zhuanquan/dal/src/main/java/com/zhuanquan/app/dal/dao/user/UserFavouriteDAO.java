package com.zhuanquan.app.dal.dao.user;

import java.util.List;

import com.zhuanquan.app.common.model.user.UserFavourite;

/**
 * 用户收藏dao
 * @author zhangjun
 *
 */
public interface UserFavouriteDAO {
	
	/**
	 * 查询收藏记录
	 * @param uid
	 * @param workId
	 * @return
	 */
	UserFavourite queryUserFavouriteRecord(long uid,long workId);
	
	/**
	 * 更新用户收藏作品的状态  
	 * @param uid
	 * @param workId
	 * @param stat 1-收藏  0-取消收藏
	 * @return
	 */
	int updateUserFavouriteRecord(long uid,long workId,int stat);
	
	/**
	 * 插入记录
	 * @param record
	 * @return
	 */
	void insertUserFavRecord(UserFavourite record);
	
	
	/**
	 * 查询所有收藏的作品
	 * @param uid
	 * @return
	 */
	List<UserFavourite> queryAllFavWork(long uid);
	
}