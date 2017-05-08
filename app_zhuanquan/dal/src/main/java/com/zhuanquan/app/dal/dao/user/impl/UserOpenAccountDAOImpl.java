package com.zhuanquan.app.dal.dao.user.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;

@Repository
public class UserOpenAccountDAOImpl extends BaseDao implements UserOpenAccountDAO {

	@Override
	public UserOpenAccount queryByOpenId(String userOpenId, int channelType) {
		
		Map map = new HashMap();
		map.put("openId", userOpenId);
		map.put("channelType", channelType);
		
		return sqlSessionTemplate.selectOne(getSqlName("queryByOpenId"), map);
		
	}

	@Override
	public int updateAccountToken(String userOpenId, int channelType, String token) {
		
		Map map = new HashMap();
		map.put("openId", userOpenId);
		map.put("channelType", channelType);
		map.put("token", token);

		return sqlSessionTemplate.update(getSqlName("updateAccountToken"), map);
	}

	@Override
	public long insertUserOpenAccount(UserOpenAccount account) {
		
		
		return sqlSessionTemplate.insert(getSqlName("insertUserOpenAccount"), account);
	}

	@Override
	public int updateToBindUid(String userOpenId, int channelType, long newUid) {
		
		Map map = new HashMap();
		map.put("openId", userOpenId);
		map.put("channelType", channelType);
		map.put("uid", newUid);
		
		return sqlSessionTemplate.update(getSqlName("updateToBindUid"), map);
	}

	@Override
	public int updateToActiveStat(String userOpenId, int channelType) {
		
		Map map = new HashMap();
		map.put("openId", userOpenId);
		map.put("channelType", channelType);
		map.put("status", 1);

		return sqlSessionTemplate.update(getSqlName("updateAccountStat"), map);
	}

	@Override
	public void updateMobilePassword(String mobile, String password) {
		
		Map map = new HashMap();
		map.put("openId", mobile);
		map.put("channelType", LoginType.CHANNEL_MOBILE);
		
		map.put("token", CommonUtil.makeEncriptPassword(password));

		sqlSessionTemplate.update(getSqlName("modifyTokenForOpenId"), map);

	}

	@Override
	public UserOpenAccount queryByUidAndChannelType(long uid, int channelType) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("channelType", channelType);

		return sqlSessionTemplate.selectOne(getSqlName("queryByUidAndChannelType"), map);
	}
	
}