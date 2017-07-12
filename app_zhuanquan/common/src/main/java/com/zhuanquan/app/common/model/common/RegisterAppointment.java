package com.zhuanquan.app.common.model.common;

import java.util.Date;

/**
 * 注册预约
 * @author zhangjun
 *
 */
public class RegisterAppointment {
	
	/**
	 * 主键id
	 */
	private Long id;
	
	
	/**
	 * 注册渠道
	 */
	private Integer channelType;
	
	
	/**
	 * token信息
	 */
	private String token;
	
	/**
	 * 第三方id，比如微博的uid
	 */
	private String openId;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 是否同步了数据
	 */
	private Integer isSyncData;


	public Integer getChannelType() {
		return channelType;
	}


	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Integer getIsSyncData() {
		return isSyncData;
	}


	public void setIsSyncData(Integer isSyncData) {
		this.isSyncData = isSyncData;
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * 创建记录
	 * @param channelType
	 * @param token
	 * @param openId
	 * @return
	 */
	public static RegisterAppointment createRecrod(int channelType,String token,String openId) {
		
		RegisterAppointment record = new RegisterAppointment();
		
		Date now =new Date();
		record.setChannelType(channelType);
		record.setCreateTime(now);
		record.setIsSyncData(0);
		record.setModifyTime(now);
		record.setOpenId(openId);
		record.setToken(token);
		
		return record;

	}

}