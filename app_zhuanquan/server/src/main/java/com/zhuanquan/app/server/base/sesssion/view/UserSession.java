package com.zhuanquan.app.server.base.sesssion.view;


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
	 * ip地址
	 */
	private long ip;
	
	/**
	 * 登录方式
	 */
	private int loginType;
	
	//uid 
	private long uid;

	public long getIp() {
		return ip;
	}

	public void setIp(long ip) {
		this.ip = ip;
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

	
	
}