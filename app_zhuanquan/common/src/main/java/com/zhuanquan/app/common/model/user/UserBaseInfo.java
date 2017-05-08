package com.zhuanquan.app.common.model.user;




public class UserBaseInfo {
	
	private Long uid;
	
	/**
	 * 头像url
	 */
	private String headUrl;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 性别
	 */
	private Integer gender;
	
	/**
	 * 手机号
	 */
	private String mobile;


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

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	
	
	
	
}