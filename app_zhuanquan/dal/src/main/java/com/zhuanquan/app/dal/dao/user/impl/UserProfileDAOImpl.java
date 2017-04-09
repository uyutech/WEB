package com.zhuanquan.app.dal.dao.user.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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

	@Override
	public int queryCountByNickName(String nickName) {
		
		List<UserProfile> list = sqlSessionTemplate.selectList(getSqlName("queryByNickName"), nickName);
		
		return CollectionUtils.isEmpty(list)?0:list.size();
	}

	@Override
	public int updateNickNameOnStep1(long uid, String nickName) {
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("nickName", nickName);

		return sqlSessionTemplate.update(getSqlName("updateNickName"), map);
	}

	@Override
	public int updateRegisterStatus(long uid, int registerStatus) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("registerStatus", registerStatus);

		return sqlSessionTemplate.update(getSqlName("updateRegisterStatus"), map);
	}

	
}