package com.zhuanquan.app.dal.dao.user.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserUpvoteWorkMappingDAO;


@Repository
public class UserUpvoteWorkMappingDAOImpl extends BaseDao implements UserUpvoteWorkMappingDAO {

	@Override
	public UserUpvoteWorkMapping queryUserUpvoteWorkMapping(long uid, long workId) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("workId", workId);
		
		return sqlSessionTemplate.selectOne(getSqlName("queryUserUpvoteWorkMapping"), map);
	}


	@Override
	public void insertOrUpdateBatchUserUpvoteWorkMapping(List<UserUpvoteWorkMapping> records) {
	
		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatchUserUpvoteWorkMapping"), records);
		
	}
	

	
}