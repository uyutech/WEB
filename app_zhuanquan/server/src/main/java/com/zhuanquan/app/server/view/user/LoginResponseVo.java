package com.zhuanquan.app.server.view.user;

import java.io.Serializable;


public class LoginResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6364221231951960984L;
	
	/**
	 * 用户名
	 */
	private String openId;
	
	/**
	 * 渠道类型
	 */
	private int channelType;
	
	/**
	 * uid
	 */
	private long uid;
	
    /**
     * 手机号
     */
    private String mobile;
	
	
	/**
	 * 是否允许关注
	 */
	private Integer allowAttation;
	
	
	/**
	 * 头像url
	 */
	private String headUrl;

	/**
	 * 昵称
	 */
	private String nickName;


	public long getUid() {
		return uid;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public int getChannelType() {
		return channelType;
	}


	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Integer getAllowAttation() {
		return allowAttation;
	}


	public void setAllowAttation(Integer allowAttation) {
		this.allowAttation = allowAttation;
	}


	public String getHeadUrl() {
		return headUrl;
	}


	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
}