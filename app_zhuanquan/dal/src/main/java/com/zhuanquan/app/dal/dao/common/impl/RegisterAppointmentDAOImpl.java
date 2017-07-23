package com.zhuanquan.app.dal.dao.common.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.common.RegisterAppointment;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.common.RegisterAppointmentDAO;


@Repository
public class RegisterAppointmentDAOImpl extends BaseDao implements RegisterAppointmentDAO {

	@Override
	public void insertOrUpdateRecord(RegisterAppointment record) {

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateRecord"), record);
		
	}

	@Override
	public List<RegisterAppointment> queryUnSyncedRecord(long fromIndex, int limit) {
		
		
		Map map = new HashMap();
		
		map.put("fromIndex", fromIndex);
		map.put("limit", limit);
		
		return sqlSessionTemplate.selectList(getSqlName("queryUnSyncedRecord"), map);
	}

	@Override
	public RegisterAppointment queryByOpenId(int channelType, String openId) {
		
		Map map = new HashMap();
		
		map.put("channelType", channelType);
		map.put("openId", openId);
		
		
		return sqlSessionTemplate.selectOne(getSqlName("queryByOpenId"), map);
	}

	@Override
	public void updateToSynced(int channelType, String openId) {
		Map map = new HashMap();
		
		map.put("channelType", channelType);
		map.put("openId", openId);		
		
		sqlSessionTemplate.update(getSqlName("updateToSynced"), map);
	}

	@Override
	public int queryRegisterAppointmentCount() {
		Object obj =  sqlSessionTemplate.selectOne(getSqlName("queryRegisterAppointmentCount"));
		
		return (int) (obj == null?0:obj);
		
	}
	
}