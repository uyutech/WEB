package com.zhuanquan.app.dal.dao.common;

import java.util.List;

import com.zhuanquan.app.common.model.common.WeiboRegisterAppointmentUserFollowData;

public interface WeiboRegisterAppointmentUserFollowDataDAO {
	
	/**
	 * 批量插入
	 * @param list
	 */
	void insertBatchRecord(List<WeiboRegisterAppointmentUserFollowData> list);
	
}