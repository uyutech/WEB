package com.zhuanquan.app.common.model.common;



/**
 * 微博预约注册用户关注的 作者id列表
 * @author zhangjun
 *
 */
public class WeiboRegisterAppointmentUserFollowData {
	
	
	/**
	 * 微博用户uid
	 */
	private String openId;
	
	
	/**
	 * 关注的微博用户id
	 */
	private String followedOpenId;


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getFollowedOpenId() {
		return followedOpenId;
	}


	public void setFollowedOpenId(String followedOpenId) {
		this.followedOpenId = followedOpenId;
	}



	
	
}