package com.zhuanquan.app.server.base.sesssion;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.framework.core.cache.redis.utils.RedisHelper;
import com.framework.core.common.utils.MD5;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.server.base.interceptor.SessionInterceptor;
import com.zhuanquan.app.server.base.sesssion.view.UserSession;
import com.zhuanquan.app.server.cache.RedisKeyBuilder;

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
	public  UserSession createOrUpdateSession(long uid, long createIp, int loginType) {
		
		String sessionKey = MD5.md5(CommonUtil.getRandomString(23));
		
		UserSession userSession = new UserSession();
		userSession.setIp(createIp);
		userSession.setUid(uid);
		userSession.setLoginType(loginType);

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
		
		redisHelper.valueSet(key, JSON.toJSONString(userSession), 24, TimeUnit.HOURS);
	
		
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