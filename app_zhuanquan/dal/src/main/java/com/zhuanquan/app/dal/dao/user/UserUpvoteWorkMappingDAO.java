package com.zhuanquan.app.dal.dao.user;

import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;

/**
 * 
 * @author zhangjun
 *
 */
public interface UserUpvoteWorkMappingDAO {
	
	
	/**
	 * 查询用户id和workid的点赞对应关系
	 * @param uid
	 * @param workId
	 * @return
	 */
	UserUpvoteWorkMapping queryUserUpvoteWorkMapping(long uid,long workId);
	
}