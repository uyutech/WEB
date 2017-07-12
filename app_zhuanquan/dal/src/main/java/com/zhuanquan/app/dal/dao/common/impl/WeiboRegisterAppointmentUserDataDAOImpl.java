package com.zhuanquan.app.dal.dao.common.impl;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.common.WeiboRegisterAppointmentUserData;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.common.WeiboRegisterAppointmentUserDataDAO;


@Repository
public class WeiboRegisterAppointmentUserDataDAOImpl extends BaseDao implements WeiboRegisterAppointmentUserDataDAO {

	@Override
	public void insertRecord(WeiboRegisterAppointmentUserData data) {
		
		
		sqlSessionTemplate.insert(getSqlName("insertRecord"), data);
	}
	
}