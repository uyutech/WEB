package com.zhuanquan.app.dal.dao.user.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserFavourite;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFavouriteDAO;

@Repository
public class UserFavouriteDAOImpl extends BaseDao implements UserFavouriteDAO {

	@Override
	public UserFavourite queryUserFavouriteRecord(long uid, long workId) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("workId", workId);
		
		return sqlSessionTemplate.selectOne(getSqlName("queryUserFavouriteRecord"), map);
	}

	@Override
	public int updateUserFavouriteRecordStat(long uid, long workId, int stat) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("workId", workId);
		map.put("status", stat);

		
		return sqlSessionTemplate.update(getSqlName("updateUserFavouriteRecord"), map);
	}

	@Override
	public void insertOrUpdateUserFavRecord(UserFavourite record) {

		
		sqlSessionTemplate.insert(getSqlName("insertOrUpdateUserFavRecord"), record);
	}

	@Override
	public List<UserFavourite> queryAllFavWork(long uid) {
		
		return sqlSessionTemplate.selectList(getSqlName("queryAllFavWork"), uid);
	}
	
}