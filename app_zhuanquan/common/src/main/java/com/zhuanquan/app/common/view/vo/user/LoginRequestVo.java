package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;

import com.zhuanquan.app.common.constants.user.LoginType;


public class LoginRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6364221231951960984L;
	
	//用户名
	private String userName;
	
	
	//密码
	private String password;
	
//	
//	//短信验证码
//	private String verifyCode;
	
	//登录方式
    private int loginType = LoginType.CHANNEL_MOBILE;


    
	public int getLoginType() {
		return loginType;
	}


	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

//
//	public String getVerifyCode() {
//		return verifyCode;
//	}
//
//
//	public void setVerifyCode(String verifyCode) {
//		this.verifyCode = verifyCode;
//	}
//	
//	
//	
	
	
}