package com.zhuanquan.app.dal.dao.user.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserFavourite;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFavouriteDAO;

@Repository
public class UserFavouriteDAOImpl extends BaseDao implements UserFavouriteDAO {

	@Override
	public UserFavourite queryUserFavouriteRecord(long uid, long workId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUserFavouriteRecord(long uid, long workId, int stat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertUserFavRecord(UserFavourite record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserFavourite> queryAllFavWork(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}