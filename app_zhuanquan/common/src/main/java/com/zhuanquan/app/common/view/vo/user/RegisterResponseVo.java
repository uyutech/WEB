package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;

public class RegisterResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713082667103526213L;



	/**
	 * uid
	 */
	private long uid;

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

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

    public RegisterResponseVo(long uid,String sessionId) {
    	this.uid  = uid;
    	this.sessionId = sessionId;
    }

	
}