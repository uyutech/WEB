package com.zhuanquan.app.server.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.server.controller.common.BaseController;



@Controller
@RequestMapping(value = "/fav")
public class UserFavController extends BaseController {
	
	
	@RequestMapping(value = "/queryUserFavInfo",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryUserFavInfo(long uid) {

		return ApiResponse.success();
	}
	
	
	
	
	
	
	
	
}