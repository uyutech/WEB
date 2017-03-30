package com.zhuanquan.app.server.service;

import com.zhuanquan.app.server.view.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.server.view.user.LoginRequestVo;
import com.zhuanquan.app.server.view.user.LoginResponseVo;

/**
 * 登录
 * @author zhangjun
 *
 */
public interface LoginService {
	
	/**
	 * 用户名密码登录
	 * @param request
	 * @return
	 */
	LoginResponseVo loginByPwd(LoginRequestVo request);
	
	
	/**
	 * 第三方登录
	 * @param request
	 * @return
	 */
	LoginResponseVo loginByOpenId(LoginByOpenIdRequestVo request);
	
	
}