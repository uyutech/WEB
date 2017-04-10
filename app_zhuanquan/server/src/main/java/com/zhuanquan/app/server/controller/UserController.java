package com.zhuanquan.app.server.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.BindAndChoosePersistRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.UserService;

//http://www.codesky.net/article/201306/181880.html

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	

	@Resource
	private RegisterService registerService;

	@Resource
	private LoginService loginService;

	@Resource
	private UserService userService;
	
	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;
	


	@RequestMapping(value = "/queryProfileByUid")
	@ResponseBody
	public ApiResponse queryProfileByUid(long uid) {

		return ApiResponse.success();
	}




}