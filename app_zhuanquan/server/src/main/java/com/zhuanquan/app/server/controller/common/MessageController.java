package com.zhuanquan.app.server.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;


@Controller
@RequestMapping(value="/message")
public class MessageController extends BaseController {
	

	@RequestMapping(value="/sendRegisterVerifyCode",produces = {"application/json"})
	@ResponseBody
	public ApiResponse sendRegisterVerifyCode(String mobile) {
		
		return ApiResponse.success();
	}
	
	
	@RequestMapping(value="/sendBindVerifyCode",produces = {"application/json"})
	@ResponseBody
	public ApiResponse sendBindVerifyCode(String mobile) {
		
		return ApiResponse.success();
	}
	
	
	
	@RequestMapping(value="/sendForgetPwdVerifyCode",produces = {"application/json"})
	@ResponseBody
	public ApiResponse sendForgetPwdVerifyCode(String mobile) {
		
		return ApiResponse.success();
	}
	
	
	
	@RequestMapping(value="/sendModifyPwdVerifyCode",produces = {"application/json"})
	@ResponseBody
	public ApiResponse sendModifyPwdVerifyCode(String mobile) {
		
		return ApiResponse.success();
	}
	
}