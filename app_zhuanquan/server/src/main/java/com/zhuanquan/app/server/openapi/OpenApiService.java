package com.zhuanquan.app.server.openapi;

import java.util.List;

import com.zhuanquan.app.common.model.common.RegisterAppointment;
import com.zhuanquan.app.common.view.bo.openapi.AuthTokenBo;

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
     
     
     /**
      * 获取token
      * @param channelType
      * @param code authcode,用来获取token
      * @return
      */
     AuthTokenBo getAuthTokenFromLoginCallBack(int channelType,String code);
     
     
     /**
      * 获取第三方登录跳转url
      * @param channelType
      * @param validateKey
      * @return
      */
     String getOpenApiLoginUrl(int channelType,String validateKey);
     
     
     /**
      * 解析微博授权回掉
      * @param stat
      * @param code
      */
     void parseWeiboAuthCallback(String stat,String code);
     

     
	
}