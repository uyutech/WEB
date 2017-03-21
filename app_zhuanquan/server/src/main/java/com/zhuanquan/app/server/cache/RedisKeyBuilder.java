package com.zhuanquan.app.server.cache;



public class RedisKeyBuilder {
	
	/**
	 * 登录的session key
	 * @param jSessionID
	 * @return
	 */
	public static String getLoginSessionKey(String jSessionID) {
		
		return "gw:login:sessionid:" + jSessionID;
	}
	
}