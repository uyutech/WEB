package com.zhuanquan.app.dal.model.user;

import java.io.Serializable;
import java.util.Date;

public class UserOpenAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711050830921914903L;
	
	
	
	/**
	 * 
	 */
	private String openId;
	
	/**
	 * 用户id
	 */
	private long uid;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	
	/**
	 * 渠道类型
	 */
	private int channelType;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	
	
	
}