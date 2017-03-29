package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.framework.core.cache.redis.utils.RedisHelper;
import com.framework.core.common.utils.MD5;
import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.utils.IpUtils;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.dal.model.user.UserProfile;
import com.zhuanquan.app.server.base.interceptor.RemoteIPInterceptor;
import com.zhuanquan.app.server.base.sesssion.SessionHolder;
import com.zhuanquan.app.server.cache.RedisKeyBuilder;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.view.user.LoginRequestVo;
import com.zhuanquan.app.server.view.user.LoginResponseVo;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private UserProfileDAO userProfileDAO;
	
	@Resource
	private SessionHolder sessionHolder;


	@Override
	public LoginResponseVo loginByPwd(LoginRequestVo request) {

		// 基本参数校验
		validateBaseParam(request);

		// 是否被限制
		validateIsLimited(request);

		UserProfile profile = userProfileDAO.queryByUserName(request.getUserName());

		// 非正常状态禁止登录
		if (profile.getStatus() != UserProfile.STATUS_NORMAL) {
			throw new BizException(BizErrorCode.EX_LOGIN_FORBIDDEN.getCode());
		}

		// md5之后和原来的不一样
		if (!profile.getPassword().equals(MD5.md5(request.getPassword()))) {
			throw new BizException(BizErrorCode.EX_LOGIN_PWD_ERR.getCode());
		}

		LoginResponseVo response = new LoginResponseVo();

		response.setUid(profile.getUid());
		response.setMobile(profile.getMobile());
		response.setUserName(profile.getUserName());
		response.setAllowAttation(profile.getAllowAttation());


		long createIp = IpUtils.ip2Long(RemoteIPInterceptor.getRemoteIP());
		sessionHolder.createOrUpdateSession(profile.getUid(), createIp,request.getLoginType());

		return response;
	}

	/**
	 * 检测参数是否合法
	 * 
	 * @param request
	 */
	private void validateBaseParam(LoginRequestVo request) {

		CommonUtil.assertNotNull(request.getPassword(), "password");

		CommonUtil.assertNotNull(request.getUserName(), "username");

	}

	/**
	 * 检测用户是否被限制了
	 */
	private void validateIsLimited(LoginRequestVo request) {

		// 是否ip限制

		
		List<String> keyList= new ArrayList<String>();
		
		

		// 短信验证码key
		String smsVerifyCode = RedisKeyBuilder.getLoginVerifyCodeKey(request.getUserName());
		
		
		String ipLimitKey = RedisKeyBuilder.getLoginIpLimitKey(RemoteIPInterceptor.getRemoteIP());

		String failTimesLimitKey = RedisKeyBuilder.getLoginFailTimesKey(request.getUserName());

		keyList.add(smsVerifyCode);
		keyList.add(ipLimitKey);
		keyList.add(failTimesLimitKey);


		List<String> valueList = redisHelper.valueMultiGet(keyList);
		
		String smsValue = valueList.get(0);
		
		String ipLimitValue = valueList.get(1);
		
		String failTimesLimitValue = valueList.get(2);

		//短信验证码校验
		if(smsValue!=null&& !smsValue.equals(request.getVerifyCode())) {
			throw new BizException(BizErrorCode.EX_LOGIN_VERIFY_CODE_ERR.getCode());
		}
		
		
		//ip限制校验
		
		
		//失败次数校验

		
	}

}