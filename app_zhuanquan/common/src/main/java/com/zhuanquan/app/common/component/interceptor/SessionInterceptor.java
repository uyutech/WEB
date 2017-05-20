package com.zhuanquan.app.common.component.interceptor;

import com.alibaba.fastjson.JSON;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.exception.SessionExpireException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 检查client_security中的Client_Security是否正确
 *
 */

public class SessionInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	/** The Constant requestLocal. */
	private static final ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<HttpServletRequest>();
	/** The Constant responseLocal. */
	private static final ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<HttpServletResponse>();

	private SessionExcludeLoader sessionExcludeLoader;

	// 读取配置文件中的private key 配置
	private final Map<String, String> privateKeyMap = new HashMap<>();

	private RedisHelper redisHelper;

	public SessionExcludeLoader getSessionExcludeLoader() {
		return sessionExcludeLoader;
	}

	public void setSessionExcludeLoader(SessionExcludeLoader sessionExcludeLoader) {
		this.sessionExcludeLoader = sessionExcludeLoader;
	}

	public RedisHelper getRedisHelper() {
		return redisHelper;
	}

	public void setRedisHelper(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}

	// 是否启用
	private boolean isDebugEnable = true;

	public void setIsDebugEnable(boolean isDebugEnable) {
		this.isDebugEnable = isDebugEnable;
	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {

		REQUEST_LOCAL.set(httpServletRequest);
		RESPONSE_LOCAL.set(httpServletResponse);
		
		//设置header
		setAccessControlHeader(httpServletRequest,httpServletResponse);

		String url = httpServletRequest.getRequestURI();

		// 测试阶段，关闭会话校验
		if (isDebugEnable) {
			return true;
		}

		// 是否在免过滤
		if (sessionExcludeLoader.isSessionCheckExclude(url)) {
			return true;
		}

		this.validateSession(httpServletRequest.getCookies(),url);

		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	
	
	
	
	
	/**
	 * 校验session
	 * 
	 * @param cookies
	 * @param params
	 * @throws SessionExpireException
	 */
	private void validateSession(Cookie[] cookies, String url) throws SessionExpireException {

		// -------------- 校验session ------------------

		// 2.1 解析sessionid
		String jSessionID = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// 解析sessionid
				if ("JSESSIONID".equals(cookie.getName())) {
					jSessionID = cookie.getValue();
					break;
				}
			}
		}

		// 2.2 , 如果cookie中没有jSessionID , 但接口又必须校验会话, 则返回 HTTP 401, 需要重新登录
		if (jSessionID == null) {
			logger.info(
					"check session failed, can not find session id in cookies, check session info failed, url {}.  ",
					url);
			throw new SessionExpireException();
		}

		// 2.3 校验会话信息
		// 会话不存在, 则返回 HTTP 401, 需要重新登录
		// 会话存在, 校验 客户端上报的 sessionid和uid, 与cached中保存的会话必须一致
		String sessionInfo;
		
		String sessionKey = RedisKeyBuilder.getLoginSessionKey(jSessionID);
		try {
			sessionInfo = redisHelper.valueGet(sessionKey);
		} catch (Exception redisException) {
			// 如果redis异常，直接放通
			logger.warn("redis exception {} when get session,session id is {}", redisException.getMessage(),
					jSessionID);
			return;
		}

		if (sessionInfo == null) {
			logger.warn("check session failed, session unmatched uid, session id {}, session info {}, url {} ",
					jSessionID, sessionInfo, url);

			throw new SessionExpireException();
		}

		//
		UserSession session = JSON.parseObject(sessionInfo, UserSession.class);
		SessionHolder.setCurrentSession(session);
		
		//会话自动续租7天
		redisHelper.expire(sessionKey, 7, TimeUnit.DAYS);

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
		clearThreadLocal();
	}

	//
	// /**
	// * 验证头 必须包含 client_secret
	// *
	// * @param params 请求参数
	// * @throws RequestHeaderInvalidateException
	// */
	// private void validateReqParams(Map<String, String> params) throws
	// RequestHeaderInvalidateException {
	// if(!params.containsKey("client_secret")){
	// logger.warn("header {} not exist or empty", "client_secret");
	// throw new RequestHeaderInvalidateException("client_secret");
	// }
	//
	// }

	/**
	 * spring setter from security-keyyml
	 *
	 * @param keyConfigMap
	 *            security key 的配置
	 */
	public void setKeyConfigMap(Map<String, Object> keyConfigMap) {

		List<Map<String, Object>> keys = (List<Map<String, Object>>) keyConfigMap.get("client_keys");

		for (Map<String, Object> one : keys) {
			privateKeyMap.put((String) one.get("type"), (String) one.get("key"));
		}
	}

	
	/**
	 * 获取当前线程的request.
	 * 
	 * @return the requestLocal
	 */
	public static HttpServletRequest getRequest() {
		return REQUEST_LOCAL.get();
	}

	/**
	 * 获取当前线程的response.
	 * 
	 * @return the responseLocal
	 */
	public static HttpServletResponse getResponse() {
		return RESPONSE_LOCAL.get();
	}
	
	
	private void clearThreadLocal() {
		
		REQUEST_LOCAL.remove();
		RESPONSE_LOCAL.remove();
		SessionHolder.removeCurrentSession();
	}
	
	
	/**
	 * 设置ajax跨域支持方案
	 * @param httpServletResponse
	 */
	private void setAccessControlHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		String origin = httpServletRequest.getHeader("origin");
		
		if(StringUtils.isNotEmpty(origin)){
			httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
			httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		}

//		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");

	}

}
