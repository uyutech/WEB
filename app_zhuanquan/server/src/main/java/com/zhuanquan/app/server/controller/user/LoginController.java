package com.zhuanquan.app.server.controller.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.component.cache.redis.GracefulRedisTemplate;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.constants.user.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
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

	@Resource
	private UserProfileDAO userProfileDAO;
	
	@Resource
	private GracefulRedisTemplate gracefulRedisTemplate;
	
	@Resource
	private RedisHelper redisHelper;

	@RequestMapping(value = "/loginByMobile", produces = { "application/json" })
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
	@RequestMapping(value = "/loginByOpenId", produces = { "application/json" })
	@ResponseBody
	public ApiResponse loginByOpenId(LoginByOpenIdRequestVo request) {

		LoginResponseVo response = loginService.loginByOpenId(request);

		return ApiResponse.success(response);
	}

	/**
	 * 检查会话状态，手机app唤醒时调用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sessionCheck", produces = { "application/json" })
	@ResponseBody
	public ApiResponse sessionCheck() {

		LoginResponseVo response = loginService.sessionCheck();

		return ApiResponse.success(response);
	}

	@RequestMapping(value = "/test", produces = { "application/json" })
	@ResponseBody
	public ApiResponse test() {

		redisHelper.rightPush("list123", "aa");
		redisHelper.rightPush("list123", "bb");
		redisHelper.rightPush("list123", "cc");


		List<String> list= gracefulRedisTemplate.listBatchLeftPop("list123", 13);
		System.out.println("obj:"+list.toString());


		return ApiResponse.success(list);
	}



	
	
	/**
	 * 获取第三方登录 授权的url地址
	 * @param channelType 见 LoginType 定义， 1表示微博
	 * @return
	 */
	@RequestMapping(value = "/getThirdLoginAuthUrl", produces = { "application/json" })
	@ResponseBody
	public ApiResponse getThirdLoginAuthUrl(int channelType) {


		String url = loginService.getThirdLoginAuthUrl(channelType);
		
		
		return ApiResponse.success(url);
	}
	

	
}