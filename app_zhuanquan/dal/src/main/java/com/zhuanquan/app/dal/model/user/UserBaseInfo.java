package com.zhuanquan.app.dal.model.user;




public class UserBaseInfo {
	
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
	
	
	
	
	
}