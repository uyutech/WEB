package com.zhuanquan.app.server.service;

import java.util.List;

/**
 * 
 * @author zhangjun
 *
 */
public interface FavouriteService {
	
	
	/**
	 * 收藏作品，保存在默认分类
	 * @param uid
	 * @param workId
	 */
	public void favouriteWork(long uid,long workId);
	
	
	/**
	 * 取消收藏作品
	 * @param uid
	 * @param workId
	 */
	public void cancelFavouriteWork(long uid,long workId);
	
	
	/**
	 * 查询所有收藏的作品
	 * @param uid
	 * @return
	 */
	List<Long> queryAllFavouriteWorks(long uid);
	
	/**
	 * 是否收藏了作品
	 * @param uid
	 * @param workId
	 * @return
	 */
	boolean hasFavouriteWork(long uid,long workId);
	
	
}