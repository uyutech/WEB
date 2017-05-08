package com.zhuanquan.app.dal.dao.user.impl;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserUpvoteWorkMappingDAO;


@Repository
public class UserUpvoteWorkMappingDAOImpl extends BaseDao implements UserUpvoteWorkMappingDAO {

	@Override
	public UserUpvoteWorkMapping queryUserUpvoteWorkMapping(long uid, long workId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> queryHasInsertWorkIds(long uid, Set<String> workIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBatchUserUpvoteWorkMapping(List<UserUpvoteWorkMapping> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBatchUserUpvoteWorkMapping(List<UserUpvoteWorkMapping> records) {
		// TODO Auto-generated method stub
		
	}
	
}