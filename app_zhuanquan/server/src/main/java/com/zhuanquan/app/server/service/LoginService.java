package com.zhuanquan.app.server.service;

import com.zhuanquan.app.server.view.user.LoginRequestVo;
import com.zhuanquan.app.server.view.user.LoginResponseVo;

/**
 * 登录
 * @author zhangjun
 *
 */
public interface LoginService {
	
	
	LoginResponseVo loginByPwd(LoginRequestVo request);
	
	
	
	
}