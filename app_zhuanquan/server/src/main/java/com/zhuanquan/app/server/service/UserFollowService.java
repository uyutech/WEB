package com.zhuanquan.app.server.service;

import java.util.List;

/**
 * 关注服务，  关注作者，关注圈子话题
 * @author zhangjun
 *
 */
public interface UserFollowService {
	

	
    /**
     * 设置用户关注的作者
     * 
     * @param uid 用户id
     * @param authorIds 作者id
     */
	void setUserFollowAuthors(long uid,List<Long> authorIds);
	
	/**
	 * 关注作者
	 * @param uid
	 * @param authorId
	 */
	void followAuthor(long uid,long authorId);
	
	/**
	 * 取消关注
	 * @param uid
	 * @param authorId
	 */
	void cancelFollowAuthor(long uid,long authorId);
	
	
}