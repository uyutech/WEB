package com.zhuanquan.app.dal.dao.common.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.common.WeiboRegisterAppointmentUserFollowData;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.common.WeiboRegisterAppointmentUserFollowDataDAO;


@Repository
public class WeiboRegisterAppointmentUserFollowDataDAOImpl extends BaseDao implements WeiboRegisterAppointmentUserFollowDataDAO {

	@Override
	public void insertBatchRecord(List<WeiboRegisterAppointmentUserFollowData> list) {

		this.sqlSessionTemplate.insert(getSqlName("insertBatchRecord"), list);
	}
	
}