package com.zhuanquan.app.common.component.sesssion;


import java.io.Serializable;


/**
 * 用户session
 * 
 * @author zhangjun
 *
 */
public class UserSession  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4350086856479856994L;


	
	/**
	 * 登录方式
	 */
	private int loginType;
	
	/**
	 * 用户id
	 */
	private long uid;
	
	/**
	 * 当前的账户类型  ，参考channeltype
	 */
	private int channelType;
	
	/**
	 * openid
	 */
	private String openId;
	
	/**
	 * 是否是大v用户
	 */
	private int isVip;

	/**
	 * 会话id
	 */
	private String sessionId;
	
	

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	} 

	
	
}