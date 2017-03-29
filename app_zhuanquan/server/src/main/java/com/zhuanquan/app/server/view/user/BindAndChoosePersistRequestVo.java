package com.zhuanquan.app.server.view.user;

import java.io.Serializable;

public class BindAndChoosePersistRequestVo implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -749210346213830540L;
	
	/**
	 * 用户id
	 */
	private long uid;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 验证码
	 */
	private String verifycode;
	
	/**
	 * 是否保留手机账号的数据 1-保留 0-保留第三方登录账号数据
	 */
	private int persistMobileAccount;

	public long getUid() {
		return uid;
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

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public int getPersistMobileAccount() {
		return persistMobileAccount;
	}

	public void setPersistMobileAccount(int persistMobileAccount) {
		this.persistMobileAccount = persistMobileAccount;
	}

	
}