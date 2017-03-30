package com.zhuanquan.app.server.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.error.exception.BizException;


import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.dal.model.user.UserProfile;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.UserService;
import com.zhuanquan.app.server.view.ApiResponse;
import com.zhuanquan.app.server.view.user.BindAndChoosePersistRequestVo;
import com.zhuanquan.app.server.view.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.server.view.user.LoginRequestVo;
import com.zhuanquan.app.server.view.user.LoginResponseVo;
import com.zhuanquan.app.server.view.user.RegisterRequestVo;
import com.zhuanquan.app.server.view.user.RegisterResponseVo;

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

	@RequestMapping(value = "/registerByMobile")
	@ResponseBody
	public ApiResponse registerByMobile(RegisterRequestVo vo) {

		RegisterResponseVo response = registerService.register(vo);

		return ApiResponse.success(response);

	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public ApiResponse login(LoginRequestVo request) {

		LoginResponseVo response = loginService.loginByPwd(request);

		return ApiResponse.success(response);

	}

	@RequestMapping(value = "/queryProfileByUid")
	@ResponseBody
	public ApiResponse queryProfileByUid(long uid) {

		return ApiResponse.success();
	}

	/**
	 * 检查手机是否已经被别人绑定了
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/beforeBindCheck")
	@ResponseBody
	public ApiResponse beforeBindCheck(String mobile) {

		UserProfile profile = userService.queryUserProfileByMobile(mobile);

		if (profile != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		return ApiResponse.success();
	}

	/**
	 * 绑定未注册过的手机号
	 * 
	 * @param uid
	 *            原来账号的uid
	 * @param mobile
	 *            手机号
	 * @param verifycode
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/bindUnregisterMobile")
	@ResponseBody
	public ApiResponse bindUnregisterMobile(long uid, String mobile, String verifycode) {

		registerService.bindUnregisterMobile(uid, mobile, verifycode);

		return ApiResponse.success();
	}

	/**
	 * 如果手机号已经注册了，那么用户需要选择，当前保留哪一个账号
	 * 
	 * @param vo
	 */
	public ApiResponse bindMobileAndChoosePersistAccount(BindAndChoosePersistRequestVo vo) {

		registerService.bindMobileAndChoosePersistAccount(vo.getUid(), vo.getMobile(), vo.getVerifycode(),
				vo.getPersistMobileAccount() == 1);

		return ApiResponse.success();
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


		return null;

	}

}