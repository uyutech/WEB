package com.zhuanquan.app.server.openapi;

import java.util.List;

import com.zhuanquan.app.common.view.bo.openapi.AuthTokenBo;
import com.zhuanquan.app.common.view.bo.openapi.WeiboUserInfoBo;

public interface OpenApiConnector {
	
	
	/**
	 * 获取当前的渠道类型
	 * @return
	 */
	int getChannelType();
	
	 /**
	  * 检测token 和 openid是否对应
	  * @param accessToken
	  * @param openId
	  * @param channelType
	  */
    void checkToken(String accessToken,String openId);
    
    
    /**
     * 获取所有关注的作者的openid
     * @param accessToken
     * @param openId
     * @param channelType
     * @return
     */
    List<String> getAllFollowedAuthorOpenIds(String accessToken,String openId);
    
    
    /**
     * 获取authtoken
     * @param code authcode
     * @return
     */
    AuthTokenBo getAuthTokenFromLoginCallBack(String code);
    
    /**
     * 获取第三方授权登录url
     * @param validateKey
     * @return
     */
    String getOpenApiAuthLoginUrl(String validateKey);
    
    /**
     * 获取用户基本信息
     * @param accessToken
     * @param openUid
     * @return
     */
    WeiboUserInfoBo getWeiboUserBaseInfo(String accessToken,String openUid);
	
}