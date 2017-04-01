package com.zhuanquan.app.dal.dao.user.impl;



import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;


@Repository
public class UserProfileDAOImpl extends BaseDao implements UserProfileDAO {

	@Override
	public UserProfile queryById(long uid) {

		return sqlSessionTemplate.selectOne(getSqlName("queryById"), uid);
	}

	@Override
	public long insertRecord(UserProfile profile) {
		return sqlSessionTemplate.insert(getSqlName("insertRecord"), profile);
	}

	
}