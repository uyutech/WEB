package com.zhuanquan.app.server.service;



public interface OpenApiService {
	
	 /**
	  * 检测token 和 openid是否对应
	  * @param accessToken
	  * @param openId
	  * @param channelType
	  */
     void checkToken(String accessToken,String openId,int channelType);
	
}