package com.zhuanquan.app.dal.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;

@Repository
public class UserOpenAccountDAOImpl extends BaseDao implements UserOpenAccountDAO {

	@Override
	public UserOpenAccount queryByOpenId(String userOpenId, int channelType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateAccountToken(String userOpenId, int channelType, String token) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insertUserOpenAccount(UserOpenAccount account) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToBindUid(String userOpenId, int channelType, long newUid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToActiveStat(String userOpenId, int channelType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void modifyPassword(String mobile, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserOpenAccount queryByUidAndChannelType(long uid, int channelType) {
		// TODO Auto-generated method stub
		return null;
	}
	
}