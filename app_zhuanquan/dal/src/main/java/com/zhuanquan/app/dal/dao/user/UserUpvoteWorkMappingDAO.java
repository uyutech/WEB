package com.zhuanquan.app.dal.dao.user;

import java.util.List;
import java.util.Set;

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
	
	
	
	/**
	 * 查询哪些 workid已经插入过了，这些已经 插入的会做update 操作
	 * @param uid
	 * @param workIds
	 * @return
	 */
	List<Long> queryHasInsertWorkIds(long uid,Set<String> workIds);
	
	
	/**
	 * 批量插入
	 * @param records
	 */
	void insertBatchUserUpvoteWorkMapping(List<UserUpvoteWorkMapping> records);
	
	
	/**
	 * 批量更新
	 * @param records
	 */
	void updateBatchUserUpvoteWorkMapping(List<UserUpvoteWorkMapping> records);
}