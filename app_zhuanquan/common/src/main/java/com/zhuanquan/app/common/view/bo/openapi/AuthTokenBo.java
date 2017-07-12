package com.zhuanquan.app.common.view.bo.openapi;

import java.io.Serializable;

public class AuthTokenBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7480261161579769427L;
	
	
	/**
	 * auth token
	 */
	private String authToken;
	
	
	/**
	 * 用户id
	 */
	private String openId;


	public String getAuthToken() {
		return authToken;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	
	
	
	
}