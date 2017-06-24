package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.author.PageQueryAuthorDynamicsVo;
import com.zhuanquan.app.common.view.vo.user.PageQueryFollowedAuthorsResponseVo;
import com.zhuanquan.app.common.view.vo.user.PageQueryFollowedTagsResponseVo;
import com.zhuanquan.app.common.view.vo.user.QueryUserFollowAuthorsResponseVo;

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
	
	/**
	 * 查询关注的作者信息
	 * @param uid
	 * @return
	 */
	QueryUserFollowAuthorsResponseVo queryFollowAuthors(long uid);
	
	
	/**
	 * 分页查询用户关注的标签
	 * @param uid
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	PageQueryFollowedTagsResponseVo queryUserFollowTags(long uid,int fromIndex,int limit);
	
	/**
	 * 分页查询用户关注的作者
	 * @param uid 
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	PageQueryFollowedAuthorsResponseVo queryUserFollowAuthors(long uid,int fromIndex,int limit);
	
	
	/**
	 * 分页查询作者动态信息
	 * @param uid
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	PageQueryAuthorDynamicsVo pageQueryFollowedAuthorDynamics(long uid,int fromIndex,int limit);
	
	
}