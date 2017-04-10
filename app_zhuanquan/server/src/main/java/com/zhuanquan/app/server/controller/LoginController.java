package com.zhuanquan.app.server.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.UserService;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	
	@Resource
	private RegisterService registerService;

	@Resource
	private LoginService loginService;

	@Resource
	private UserService userService;

	
	
	@RequestMapping(value = "/loginByMobile")
	@ResponseBody
	public ApiResponse loginByMobile(LoginRequestVo request) {

		LoginResponseVo response = loginService.loginByPwd(request);

		return ApiResponse.success(response);

	}
	
	
	
	/**
	 * 第三方登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginByOpenId")
	@ResponseBody
	public ApiResponse loginByOpenId(LoginByOpenIdRequestVo request) {

		LoginResponseVo response  = loginService.loginByOpenId(request);
		
		return ApiResponse.success(response);
	}
	
	
	
	
}