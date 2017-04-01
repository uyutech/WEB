package com.zhuanquan.app.common.utils;





public class PropertiesLoader {
	
	
	
	/**
	 * ip 登录限制，频繁登录的被限制
	 * @return
	 */
	public static boolean isEnableLoginIpLimit(){
		return false;
		
	}
	
	
	/**
	 * 登录失败次数
	 * @return
	 */
	public static boolean isEnableLoginFailTimesLimit(){
		return false;
		
	}
}