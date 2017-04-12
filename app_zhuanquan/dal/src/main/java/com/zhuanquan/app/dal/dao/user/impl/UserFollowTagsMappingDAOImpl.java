package com.zhuanquan.app.dal.dao.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserFollowTagsMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;


@Repository
public class UserFollowTagsMappingDAOImpl extends BaseDao implements UserFollowTagsMappingDAO {

	@Override
	public void insertBatchToFollowTags(long uid, List<UserFollowTagsMapping> tagMappings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBatchToFollowTags(long uid, List<Long> tagIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> queryByTagIds(long uid, List<Long> tagIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
}