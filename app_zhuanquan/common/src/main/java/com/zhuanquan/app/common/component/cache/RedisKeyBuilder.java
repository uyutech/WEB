package com.zhuanquan.app.common.component.cache;



public class RedisKeyBuilder {
	
	/**
	 * 登录的session key
	 * @param jSessionID
	 * @return
	 */
	public static String getLoginSessionKey(String jSessionID) {
		
		return "gw:login:sessionid:" + jSessionID;
	}
	
	
	/**
	 * 短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getRegisterSmsVerfiyCodeKey(String mobile) {
		
		return "gw:register:mobile:"+mobile;
	}
	
	
	
	/**
	 * 登录的ip限制 key
	 * @param mobile
	 * @return
	 */
	public static String getLoginIpLimitKey(String ip) {
		
		return "gw:login:ipLimit:"+ip;
	}
	
	
	
	/**
	 * 登录的失败次数
	 * @param mobile
	 * @return
	 */
	public static String getLoginFailTimesKey(String userName) {
		
		return "gw:login:failtimes:"+userName;
	}
	
	
	/**
	 * 登录的短信验证码
	 * @param mobile
	 * @return
	 */
	public static String getLoginVerifyCodeKey(String userName) {
		
		return "gw:login:verifycode:"+userName;
	}
	
	
	/**
	 * 绑定手机号短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getBindMobileSmsVerfiyCodeKey(String mobile) {
		
		return "gw:bind:mobile:"+mobile;
	}
	
	
	
}


