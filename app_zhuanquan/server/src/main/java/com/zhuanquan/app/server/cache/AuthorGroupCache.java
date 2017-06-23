package com.zhuanquan.app.server.cache;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorGroupBase;

/**
 * 组合
 * 
 * @author zhangjun
 *
 */
public interface AuthorGroupCache {
	
	/**
	 * 查询group基础信息
	 * @param groupId
	 * @return
	 */
	AuthorGroupBase queryGroupBase(long groupId);
	

	/**
	 * 查询作者所在group组合
	 * @param authorId
	 * @return
	 */
	List<AuthorGroupBase> queryAuthorGroup(long authorId);
	
}