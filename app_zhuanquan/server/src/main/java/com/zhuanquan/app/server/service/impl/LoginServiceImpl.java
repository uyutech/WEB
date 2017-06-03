package com.zhuanquan.app.server.service.impl;


import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;

import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.ThirdChannelSyncFollowAuthorRequestBo;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.common.view.vo.user.LoginResponseVo;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.server.cache.UserOpenAccountCache;
import com.zhuanquan.app.server.openapi.OpenApiService;
import com.zhuanquan.app.server.service.LoginService;
import com.zhuanquan.app.server.service.TransactionService;
import com.zhuanquan.app.server.thread.ThirdChannelSyncThreadPool;

@Service
public class LoginServiceImpl implements LoginService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 失败次数
	private static final long FAIL_TIMES_LIMIT = 3;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private UserProfileDAO userProfileDAO;

	@Resource
	private SessionHolder sessionHolder;

	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;

	@Resource
	private TransactionService transactionService;

	@Resource
	private OpenApiService openApiService;

	@Resource
	private UserOpenAccountCache userOpenAccountCache;

	@Override
	public LoginResponseVo loginByPwd(LoginRequestVo request) {

		// 基本参数校验
		validateBaseParam(request);

		// 是否被限制
		validateIsLimited(request);

		UserOpenAccount account = userOpenAccountCache.queryByOpenId(request.getUserName(), LoginType.CHANNEL_MOBILE);

		// 如果account为null
		if (account == null) {
			throw new BizException(BizErrorCode.EX_LOGIN_PWD_ERR.getCode());
		}

		// 校验密码
		verifyPwd(account, request.getUserName(), request.getPassword());

		UserProfile profile = userProfileDAO.queryById(account.getUid());

		if (profile == null) {
			throw new BizException(BizErrorCode.EX_UID_NOT_EXSIT.getCode());
		}

		// 非正常状态禁止登录
		if (profile.getStatus() != UserProfile.STATUS_NORMAL) {
			throw new BizException(BizErrorCode.EX_LOGIN_FORBIDDEN.getCode());
		}

		return sessionCreate(profile, request.getLoginType(), request.getUserName(), LoginType.CHANNEL_MOBILE,
				profile.getIsVip());
	}

	/**
	 * 校验密码
	 * 
	 * @param account
	 * @param username
	 * @param pwd
	 */
	private void verifyPwd(UserOpenAccount account, String username, String pwd) {

		HttpSession session = SessionHolder.getGlobalSession();

		// md5之后和原来的不一样
		if (!account.getToken().equals(CommonUtil.makeEncriptPassword(pwd))) {

			// 如果密码不一致，失败次数加1，次数为3那么直接设置flag
			String failTimesLimitKey = RedisKeyBuilder.getLoginFailTimesKey(username);

//			long currentFailTimes = redisHelper.valueGetLong(failTimesLimitKey);

			
			redisHelper.increase(failTimesLimitKey, 1);
			redisHelper.expire(failTimesLimitKey, 5, TimeUnit.MINUTES);
//			
//			if (currentFailTimes < FAIL_TIMES_LIMIT - 1) {
//				redisHelper.increase(failTimesLimitKey, 1);
//
//				redisHelper.expire(failTimesLimitKey, 15, TimeUnit.MINUTES);
//				
//			} else if (currentFailTimes == FAIL_TIMES_LIMIT - 1) {
//				redisHelper.increase(failTimesLimitKey, 1);
//				redisHelper.expire(failTimesLimitKey, 15, TimeUnit.MINUTES);
//
//				session.setAttribute(SessionAttrbute.VERIFY_CODE_FLAG, "1");
//			} else {
//				session.setAttribute(SessionAttrbute.VERIFY_CODE_FLAG, "1");
//
//			}

			throw new BizException(BizErrorCode.EX_LOGIN_PWD_ERR.getCode());
		} else {
			String failTimesLimitKey = RedisKeyBuilder.getLoginFailTimesKey(username);

//			// 密码校验对了，就清理掉这个flag和redis里的失败次数统计
//			session.removeAttribute(SessionAttrbute.VERIFY_CODE_FLAG);
			redisHelper.delete(failTimesLimitKey);
		}

	}

	/**
	 * 创建会话
	 * 
	 * @param profile
	 * @param loginType
	 *            登录方式，web还是client端
	 * @param openId
	 *            openid
	 * @param channelType
	 *            登录渠道类型
	 * @param isVip
	 *            是否知名大v
	 * @return
	 */
	private LoginResponseVo sessionCreate(UserProfile profile, int loginType, String openId, int channelType,
			int isVip) {

		LoginResponseVo response = new LoginResponseVo();

		response.setUid(profile.getUid());
		response.setAllowAttation(profile.getAllowFollow());
		response.setChannelType(channelType);
		response.setHeadUrl(profile.getHeadUrl());
		response.setNickName(profile.getNickName());
		response.setOpenId(openId);

		// 设置登录后注册的状态，需要根据状态决定是否跳转到注册引导页面
		response.setRegStat(profile.getRegStat());

		response.setIsVip(isVip);

		UserSession session = sessionHolder.createOrUpdateSession(profile.getUid(), loginType, openId, channelType,
				isVip);

		response.setSessionId(session.getSessionId());

		return response;
	}

	/**
	 * 检测基本参数是否合法
	 * 
	 * @param request
	 */
	private void validateBaseParam(LoginRequestVo request) {

		CommonUtil.assertNotNull(request.getUserName(), "username");

		CommonUtil.assertNotNull(request.getPassword(), "password");

	}

	/**
	 * 检测用户是否被限制登录
	 */
	private void validateIsLimited(LoginRequestVo request) {

		// 是否ip限制

//		// 图片验证码key
//		String picVerifyCode = RedisKeyBuilder.getLoginVerifyCodeKey(request.getUserName());

		// String ipLimitKey =
		// RedisKeyBuilder.getLoginIpLimitKey(RemoteIPInterceptor.getRemoteIP());

		String failTimesLimitKey = RedisKeyBuilder.getLoginFailTimesKey(request.getUserName());

		// 失败次数
		long failTimes = redisHelper.valueGetLong(failTimesLimitKey);

		// 失败次数大于等于3，需要校验验证码
		if (failTimes >= FAIL_TIMES_LIMIT) {

			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_TOO_MANY_FAIL_TIMES_ERROR.getCode());
//			String picValue = redisHelper.valueGet(picVerifyCode);
//			// 图片验证码校验
//			if (picValue != null && !picValue.equals(request.getVerifyCode())) {
//				throw new BizException(BizErrorCode.EX_LOGIN_VERIFY_CODE_ERR.getCode());
//			}

		}

	}

	@Override
	public LoginResponseVo loginByOpenId(LoginByOpenIdRequestVo request) {

		// 基本校验
		request.validat();

		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(request.getOpenId(), request.getChannelType());

		// 如果account为null，那么去创建个记录
		if (account == null) {

			// 先检测第三方的token是否合法
			openApiService.checkToken(request.getToken(), request.getOpenId(), request.getChannelType());

			// 第三方登录注册
			UserProfile profile = transactionService.normalOpenAccountRegister(request);

			//第三方注册成功，启动异步线程去同步第三方关注列表，查询有无关注的作者与我们库中相匹配的，放到注册推荐的作者缓存中去，有效时间15分钟
			//超过有效期之后，用户即使再去设置注册信息，推荐作者里就只会推荐我们网站热度高的作者，不会再推荐他的第三方里的作者
			ThirdChannelSyncFollowAuthorRequestBo bo = new ThirdChannelSyncFollowAuthorRequestBo();
			bo.setChannelType(request.getChannelType());
			bo.setOpenId(request.getOpenId());
			bo.setToken(request.getToken());
			bo.setUid(profile.getUid());
			ThirdChannelSyncThreadPool.scheduleSyncFollowAuthorRequest(bo);
			
			
			// 
			return sessionCreate(profile, request.getLoginType(), request.getOpenId(), request.getChannelType(),
					UserOpenAccount.NORMAL_ACCOUNT);
		}

		// 如果记录存在
		// 如果token不一样，更新token
		if (!account.getToken().equals(request.getToken())) {

			// 先检测token是否合法
			openApiService.checkToken(request.getToken(), request.getOpenId(), request.getChannelType());

			// 如果状态为 unactive，那么必然是 预先生成的vip账号，那么先激活这个账号
			if (account.getStatus() == UserOpenAccount.STATS_UNACTIVE) {
				// 更新到激活状态
				userOpenAccountDAO.updateToActiveStat(account.getOpenId(), account.getChannelType());
			}

			userOpenAccountDAO.updateAccountToken(account.getOpenId(), account.getChannelType(), request.getToken());
		}

		long uid = account.getUid();

		UserProfile profile = userProfileDAO.queryById(uid);

		// 非正常状态禁止登录
		if (profile.getStatus() != UserProfile.STATUS_NORMAL) {
			throw new BizException(BizErrorCode.EX_LOGIN_FORBIDDEN.getCode());
		}

		return sessionCreate(profile, request.getLoginType(), request.getOpenId(), request.getChannelType(),
				profile.getIsVip());

	}

	@Override
	public LoginResponseVo sessionCheck() {
		
		UserSession session = SessionHolder.getCurrentLoginUserInfo();	
		
		if(session == null) {
			throw new BizException(BizErrorCode.EX_SESSION_EXPIRE.getCode());
		}

		LoginResponseVo response = new LoginResponseVo();
		
		
		UserProfile profile = userProfileDAO.queryById(session.getUid());

		if(profile == null) {
			throw new BizException(BizErrorCode.EX_SYSTEM_ERROR.getCode());
		}
		
		response.setUid(session.getUid());
		response.setAllowAttation(profile.getAllowFollow());
		response.setChannelType(session.getChannelType());
		response.setHeadUrl(profile.getHeadUrl());
		response.setNickName(profile.getNickName());
		response.setOpenId(session.getOpenId());

		// 设置登录后注册的状态，需要根据状态决定是否跳转到注册引导页面
		response.setRegStat(profile.getRegStat());
		response.setIsVip(profile.getIsVip());


		response.setSessionId(session.getSessionId());

		return response;
	}

}