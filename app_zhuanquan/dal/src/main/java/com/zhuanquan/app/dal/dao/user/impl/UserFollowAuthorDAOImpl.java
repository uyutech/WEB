package com.zhuanquan.app.dal.dao.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;

@Repository
public class UserFollowAuthorDAOImpl extends BaseDao implements UserFollowAuthorDAO {

	@Override
	public void insertOrUpdateToFollowAuthor(long uid, long authId) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateToCancelFollowAuthor(long uid, long authId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryUserFollowAuthorCount(long uid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Long> queryUserFollowAuthorIds(long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryAuthorFollowersCount(long authId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Long> queryAuthorFollowersIds(long authId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBatchFollowAuthorIds(long uid, List<Long> authIds) {
		// TODO Auto-generated method stub

	}

}