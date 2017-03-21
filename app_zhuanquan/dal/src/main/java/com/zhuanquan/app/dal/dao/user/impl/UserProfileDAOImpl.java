package com.zhuanquan.app.dal.dao.user.impl;


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
	
}