package com.zhuanquan.app.dal.dao.common;

import java.util.List;

import com.zhuanquan.app.common.model.common.RegisterAppointment;

/**
 * 注册预约
 * @author zhangjun
 *
 */
public interface RegisterAppointmentDAO {
	
	/**
	 * 注册插入记录
	 * @param record
	 */
	void insertOrUpdateRecord(RegisterAppointment record);
	
	/**
	 * 查询注册预约记录中未处理的
	 * @param fromIndex 从哪个id开始
	 * @param limit 一次查几个
	 * @return
	 */
	List<RegisterAppointment> queryUnSyncedRecord(long fromIndex,int limit);
	
	/**
	 *  根据openid查询
	 * @param channelType
	 * @param openId
	 * @return
	 */
	RegisterAppointment queryByOpenId(int channelType,String openId);
	
	
	
	/**
	 * 状态改为已同步
	 * @param channelType
	 * @param openId
	 */
	void updateToSynced(int channelType,String openId);
	
	/**
	 * 查询预约人数
	 * @return
	 */
	int queryRegisterAppointmentCount();
	
}