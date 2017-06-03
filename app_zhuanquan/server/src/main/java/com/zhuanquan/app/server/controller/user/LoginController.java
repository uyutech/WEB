package com.zhuanquan.app.server.controller.user;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.RegisterService;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	
	@Resource
	private RegisterService registerService;

	@Resource
	private LoginService loginService;


	
	
	@RequestMapping(value = "/loginByMobile",produces = {"application/json"})
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
	@RequestMapping(value = "/loginByOpenId",produces = {"application/json"})
	@ResponseBody
	public ApiResponse loginByOpenId(LoginByOpenIdRequestVo request) {

		LoginResponseVo response  = loginService.loginByOpenId(request);
		
		return ApiResponse.success(response);
	}
	
	
	/**
	 * 检查会话状态，手机app唤醒时调用
	 * @return
	 */
	@RequestMapping(value = "/sessionCheck",produces = {"application/json"})
	@ResponseBody
	public ApiResponse sessionCheck() {
		
		LoginResponseVo response = loginService.sessionCheck();

		return ApiResponse.success(response);		
	}
	
}