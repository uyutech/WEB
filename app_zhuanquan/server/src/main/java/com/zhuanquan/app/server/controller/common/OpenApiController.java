package com.zhuanquan.app.server.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.server.openapi.OpenApiService;

@Controller
@RequestMapping(value = "/openapi")
public class OpenApiController extends BaseController {
	
	
	@Resource
	private OpenApiService openApiService;
	

	/**
	 * 微博登录授权回掉
	 */
	@RequestMapping(value = "/weiboAuthCallback", produces = { "application/json" })
	@ResponseBody
	public ApiResponse weiboAuthCallback(String code,String state) {
		
		openApiService.parseWeiboAuthCallback(state, code);
		
		return ApiResponse.success();
	}
	
	
}