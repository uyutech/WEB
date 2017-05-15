package com.zhuanquan.app.server.controller.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;


//基类，统一处理所有的异常
public class BaseController {

	/**
	 * cotroller 都是单例，不用static字段
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取登录uid
	 * @return
	 */
	public long getCurrentLoginUid() {
		
		return SessionHolder.getCurrentLoginUid();

	}
	
	
	/**
	 * 获取当前登录的session
	 * @return
	 */
	public UserSession getLoginUserSession() {
		
		return SessionHolder.getCurrentLoginUserInfo();
	}
	
	
	
}
