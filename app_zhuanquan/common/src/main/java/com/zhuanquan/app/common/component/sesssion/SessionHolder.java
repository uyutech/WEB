package com.zhuanquan.app.common.component.sesssion;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.framework.core.cache.redis.utils.RedisHelper;
import com.framework.core.common.utils.MD5;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.interceptor.SessionInterceptor;
import com.zhuanquan.app.common.utils.CommonUtil;


@Component
public class SessionHolder  {

	@Resource
	private RedisHelper redisHelper;
	
	
	private static final ThreadLocal<UserSession> SESSION_LOCAL = new ThreadLocal<UserSession>();

	
	/**
	 * 
	 * @param uid
	 * @param createIp
	 * @param loginType 登录方式
	 * @return
	 */
	
	
	/**
	 * 创建会话
	 * @param uid 用户id
	 * @param loginType 登录类型 web还是client
	 * @param openId openid
	 * @param channelType 账户频道类型
	 * @param isVip 是否大v用户
	 * @return
	 */
	public  UserSession createOrUpdateSession(long uid, int loginType,String openId, int channelType, int isVip) {
		
		String sessionKey = MD5.md5(CommonUtil.getRandomString(23));
		
		UserSession userSession = new UserSession();
		userSession.setUid(uid);
		userSession.setLoginType(loginType);
		
		userSession.setChannelType(channelType);
		userSession.setOpenId(openId);
		userSession.setIsVip(isVip);

		SESSION_LOCAL.set(userSession);


		HttpServletResponse response = SessionInterceptor.getResponse();
		// 添加到cookie
		if (response != null) {
			// 注册成功之后, 设置cookie
			Cookie cookie = new Cookie("JSESSIONID", sessionKey);
			cookie.setMaxAge(24 * 60 * 60); // 24小时
			response.addCookie(cookie);
		}

		String key = RedisKeyBuilder.getLoginSessionKey(sessionKey);
		redisHelper.valueSet(key, JSON.toJSONString(userSession), 7, TimeUnit.DAYS);
		
		return userSession;
	}
	
	/**
	 * 设置当前登录的session
	 * @param userSession
	 */
	public static void setCurrentSession(UserSession userSession){
		
		if(userSession!=null) {
			SESSION_LOCAL.set(userSession);
		}	
	}

	/**
	 * 清理session
	 */
	public static void removeCurrentSession(){
		
		SESSION_LOCAL.remove();
	}
	
	
	/**
	 * 获取当前登录的uid
	 * @return
	 */
	public static long getCurrentLoginUid() {
		
		UserSession session = SESSION_LOCAL.get();
		
		if(session == null) {
			return -1;
		} else {
			return session.getUid();
		}

	}
	
	
	/**
	 * 获取当前登录的session信息
	 * @return
	 */
	public static UserSession getCurrentLoginUserSession() {
		return SESSION_LOCAL.get();
	}

}