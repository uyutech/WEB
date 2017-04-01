package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.framework.core.cache.redis.utils.RedisHelper;
import com.framework.core.common.utils.MD5;
import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.interceptor.RemoteIPInterceptor;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.utils.IpUtils;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;

import com.zhuanquan.app.server.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private UserProfileDAO userProfileDAO;
	
	@Resource
	private SessionHolder sessionHolder;
	
	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;


	@Override
	public LoginResponseVo loginByPwd(LoginRequestVo request) {

		// 基本参数校验
		validateBaseParam(request);

		// 是否被限制
		validateIsLimited(request);

		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(request.getUserName(), ChannelType.CHANNEL_MOBILE);
		
		//如果account为null
		if(account == null) {
			throw new BizException(BizErrorCode.EX_UID_NOT_EXSIT.getCode());
		}

		// md5之后和原来的不一样
		if (!account.getToken().equals(MD5.md5(request.getPassword()))) {
			throw new BizException(BizErrorCode.EX_LOGIN_PWD_ERR.getCode());
		}
		

		UserProfile profile = userProfileDAO.queryById(account.getUid());
		
		if(profile == null) {
			throw new BizException(BizErrorCode.EX_UID_NOT_EXSIT.getCode());
		}
		
		// 非正常状态禁止登录
		if (profile.getStatus() != UserProfile.STATUS_NORMAL) {
			throw new BizException(BizErrorCode.EX_LOGIN_FORBIDDEN.getCode());
		}



		return sessionCreate(profile, request.getLoginType(),request.getUserName(),ChannelType.CHANNEL_MOBILE);
	}

	
	private LoginResponseVo sessionCreate(UserProfile profile,int loginType,String openId,int channelType) {
		
		LoginResponseVo response = new LoginResponseVo();

		response.setUid(profile.getUid());
		response.setAllowAttation(profile.getAllowAttation());
		response.setAllowAttation(profile.getAllowAttation());
		response.setChannelType(channelType);
		response.setHeadUrl(profile.getHeadUrl());
		response.setNickName(profile.getNickName());
		response.setOpenId(openId);
		response.setRegStat(profile.getRegisterStat());
		
		sessionHolder.createOrUpdateSession(profile.getUid(), loginType,openId,channelType);
		
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

	@Override
	public LoginResponseVo loginByOpenId(LoginByOpenIdRequestVo request) {
		
		//基本校验
		request.validat();
		
		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(request.getOpenId(), request.getChannelType());
		
		//如果记录存在
		if(account != null) {
			
			//如果token不一样，更新token
			if(!account.getToken().equals(request.getToken())) {
				userOpenAccountDAO.updateAccountToken(account.getOpenId(), account.getChannelType(), request.getToken());
			}
			
			long uid = account.getUid();
			
			UserProfile profile = userProfileDAO.queryById(uid);
			
			// 非正常状态禁止登录
			if (profile.getStatus() != UserProfile.STATUS_NORMAL) {
				throw new BizException(BizErrorCode.EX_LOGIN_FORBIDDEN.getCode());
			}

			return sessionCreate(profile, request.getLoginType(),request.getOpenId(),request.getChannelType());
		}
		
		
		
		
		
		
		
		return null;
	}

}