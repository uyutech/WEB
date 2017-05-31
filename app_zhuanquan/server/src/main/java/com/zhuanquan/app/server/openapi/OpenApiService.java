package com.zhuanquan.app.server.openapi;

import java.util.List;

public interface OpenApiService {
	
	 /**
	  * 检测token 和 openid是否对应
	  * @param accessToken
	  * @param openId
	  * @param channelType
	  */
     void checkToken(String accessToken,String openId,int channelType);
     
     
     /**
      * 获取所有关注的作者的openid
      * @param accessToken
      * @param openId
      * @param channelType
      * @return
      */
     List<String> getAllFollowedAuthorOpenIds(String accessToken,String openId,int channelType);
	
}