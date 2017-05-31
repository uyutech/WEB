package com.zhuanquan.app.server.cache;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorHotIndexes;

/**
 * 作者热度指数cache
 * @author zhangjun
 *
 */
public interface AuthorHotIndexesCache {
	
	/**
	 * 获取top 100 热度作者
	 * @return
	 */
	List<AuthorHotIndexes> getAuthorHotIndexTop100();
	

	
}