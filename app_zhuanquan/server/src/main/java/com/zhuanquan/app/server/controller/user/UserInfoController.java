package com.zhuanquan.app.server.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;

import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.UserService;

//

@Controller
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {
	

	@Resource
	private RegisterService registerService;

	@Resource
	private LoginService loginService;

	@Resource
	private UserService userService;
	



	@RequestMapping(value = "/queryProfileByUid")
	@ResponseBody
	public ApiResponse queryProfileByUid(long uid) {

		return ApiResponse.success();
	}



	
	
	
	

}