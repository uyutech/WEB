package com.zhuanquan.app.server.view.user;

import java.io.Serializable;

public class RegisterResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713082667103526213L;

	/**
	 * 用户名
	 */
	private String userName;

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

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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