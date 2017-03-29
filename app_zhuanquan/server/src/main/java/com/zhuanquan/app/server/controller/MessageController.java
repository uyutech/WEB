package com.zhuanquan.app.server.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.server.view.ApiResponse;

@Controller
@RequestMapping(value="/message")
public class MessageController extends BaseController {
	

	@RequestMapping(value="/sendRegisterVerifyCode")
	@ResponseBody
	public ApiResponse sendRegisterVerifyCode(String mobile) {
		
		return ApiResponse.success();
	}
	
}