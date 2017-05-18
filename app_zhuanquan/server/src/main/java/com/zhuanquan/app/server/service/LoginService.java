package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;

/**
 * 登录
 * @author zhangjun
 *
 */
public interface LoginService {
	
	
	/**
	 * 检查会话状态，手机app唤醒时调用
	 * @return
	 */
	LoginResponseVo sessionCheck();

	
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