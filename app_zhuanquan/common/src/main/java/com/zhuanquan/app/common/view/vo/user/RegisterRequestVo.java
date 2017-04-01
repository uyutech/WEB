package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;

/**
 * 注册request对象
 * 
 * @author zhangjun
 *
 */
public class RegisterRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7020611124345016364L;


	// 密码
	private String password;

	// 手机号
	private String profile;
	
	//短信验证码
	private String verifyCode;


    //注册方式：邮箱-0   手机号-1 
	private int regType = 1;
	
	//0-客户端 1-web端
	private int loginType = 0;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}



	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}


}