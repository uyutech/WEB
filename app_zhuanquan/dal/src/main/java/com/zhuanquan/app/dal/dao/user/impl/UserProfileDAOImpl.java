package com.zhuanquan.app.dal.dao.user.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.dal.model.user.UserProfile;


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

	@Override
	public UserProfile queryByUserName(String userName) {
		return sqlSessionTemplate.selectOne(getSqlName("queryByUserName"), userName);
	}

	@Override
	public void bindMobile(long uid,String mobile) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("mobile", mobile);

		
		sqlSessionTemplate.update(getSqlName("bindMobile"), map);
	}
	
}