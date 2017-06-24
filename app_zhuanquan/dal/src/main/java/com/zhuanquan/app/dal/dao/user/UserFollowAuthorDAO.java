package com.zhuanquan.app.dal.dao.user;

import java.util.List;

/**
 * 用户关注的作者
 * @author zhangjun
 *
 */
public interface UserFollowAuthorDAO {
	
	/**
	 * 关注某个作者
	 * @param uid
	 * @param authId
	 */
	void insertOrUpdateToFollowAuthor(long uid,long authId);
	
	
	/**
	 * 取消关注某个作者
	 * @param uid
	 * @param authId
	 * @return
	 */
	int updateToCancelFollowAuthor(long uid,long authId);
	
	
	/**
	 * 查询用户关注的作者总数
	 * @param uid
	 * @return
	 */
	int queryUserFollowAuthorCount(long uid);
	
	
	/**
	 * 查询关注的作者列表
	 * @param uid
	 * @return
	 */
	List<Long> queryUserFollowAuthorIds(long uid);
	
	
	
	/**
	 *  查询这个作者的粉丝数
	 * @param authId
	 * @return
	 */
	int queryAuthorFollowersCount(long authId);
	
	
	/**
	 * 查询作者的粉丝ids
	 * @param authId
	 * @return
	 */
	List<Long> queryAuthorFollowersIds(long authId);
	
	
	/**
	 * 批量插入关注的作者信息
	 * 
	 * @param uid
	 * @param authIds
	 */
	void insertBatchFollowAuthorIds(long uid,List<Long> authIds);
	
	/**
	 * 查询是否关注了
	 * @param uid
	 * @param workId
	 * @return
	 */
	boolean queryHasFollowAuthor(long uid, long authId);
	
	
	/**
	 * 分页查询关注作者
	 * @param uid
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<Long> queryFollowAuthorsByPage(long uid,int limit ,int offset);

	
	
}