package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.framework.core.cache.redis.utils.RedisHelper;
import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.interceptor.RemoteIPInterceptor;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.constants.LoginTypeEnum;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.utils.IpUtils;
import com.zhuanquan.app.common.utils.PhoneValidateUtils;
import com.zhuanquan.app.common.view.vo.user.OpenApiRegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;

import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.TransactionService;


@Service
public class RegisterServiceImpl implements RegisterService {

	@Resource
	private UserProfileDAO userProfileDAO;

	@Resource
	private SessionHolder sessionHolder;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private TransactionService transactionService;

	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;

	@Override
	public RegisterResponseVo mobileRegister(RegisterRequestVo vo) {

		// 基本参数校验
		validateBaseParam(vo);

		// 短信验证码校验
		validateSmsCode(vo);

		RegisterResponseVo response = transactionService.registerMobile(vo);


		sessionHolder.createOrUpdateSession(response.getUid(), vo.getLoginType(),vo.getProfile(),ChannelType.CHANNEL_MOBILE);

		return response;
	}

	/**
	 * 基础校验
	 * 
	 * @param vo
	 */
	private void validateBaseParam(RegisterRequestVo vo) {

		CommonUtil.assertNotNull(vo.getPassword(), "password");
		CommonUtil.assertNotNull(vo.getProfile(), "profile");
		CommonUtil.assertNotNull(vo.getVerifyCode(), "verifyCode");

		// 校验手机号码是否合法
		PhoneValidateUtils.isPhoneLegal(vo.getProfile());

		// 校验密码长度等

	}

	/**
	 * 验证短信验证码是否正确
	 * 
	 * @param smsCode
	 */
	private void validateSmsCode(RegisterRequestVo vo) {

		String verifyCodeKey = RedisKeyBuilder.getRegisterSmsVerfiyCodeKey(vo.getProfile());

		String smsCode = redisHelper.valueGet(verifyCodeKey);

		// 校验验证码是否正确
		if (!vo.getVerifyCode().equals(smsCode)) {
			throw new BizException(BizErrorCode.EX_REGISTER_VERIFY_CODE_ERR.getCode());
		}

	}

	private boolean registerValidatePassword(String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		String reg = "^[a-zA-Z0-9~!@#$%^&*()-_+=<,>./?;:\"'{\\[}\\]\\|]{6,20}$";
		return password.matches(reg);
	}

	@Override
	public void bindUnregisterMobile(long uid, String mobile, String password, String verifycode) {

		long currentLoginUid = SessionHolder.getCurrentLoginUid();

		// 判断传入的uid与当前登录的用户uid是否一致
		if (uid != currentLoginUid) {
			throw new BizException(BizErrorCode.EX_UID_NOT_CURRENT_LOGIN_USER.getCode());
		}

		// 短信验证码不为空
		if (StringUtils.isEmpty(verifycode)) {
			throw new BizException(BizErrorCode.EX_VERIFY_CODE_EMPTY.getCode());
		}

		// 验证手机是否合法的号码
		PhoneValidateUtils.isPhoneLegal(mobile);

		// 校验短信验证码是否正确
		validateBindSmsCode(mobile, verifycode);

		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(mobile, ChannelType.CHANNEL_MOBILE);

		// 已经被注册了直接报异常
		if (account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		// account创建
		account = UserOpenAccount.createMobileAccount(mobile, password, uid);

		userOpenAccountDAO.insertUserOpenAccount(account);

	}

	/**
	 * 验证短信验证码是否正确
	 * 
	 * @param smsCode
	 */
	private void validateBindSmsCode(String mobile, String code) {

		String verifyCodeKey = RedisKeyBuilder.getBindMobileSmsVerfiyCodeKey(mobile);

		String smsCode = redisHelper.valueGet(verifyCodeKey);

		// 校验验证码是否正确
		if (!code.equals(smsCode)) {
			throw new BizException(BizErrorCode.EX_REGISTER_VERIFY_CODE_ERR.getCode());
		}

	}

	@Override
	public void mergeMobileAccount(long uid, String mobile, String verifycode,
			boolean persistMobileAccount) {

		
		// 短信验证码不为空
		if (StringUtils.isEmpty(verifycode)) {
			throw new BizException(BizErrorCode.EX_VERIFY_CODE_EMPTY.getCode());
		}
		
		
		
		// 验证手机是否合法的号码
		PhoneValidateUtils.isPhoneLegal(mobile);

		// 校验短信验证码是否正确
		validateBindSmsCode(mobile, verifycode);


		UserSession session = SessionHolder.getCurrentLoginUserSession();

//		long currentLoginUid = SessionHolder.getCurrentLoginUid();

		// 判断传入账户的uid与当前登录的用户uid是否一致
		if (session == null || uid != session.getUid()) {
			throw new BizException(BizErrorCode.EX_UID_NOT_CURRENT_LOGIN_USER.getCode());
		}


			
		//
		UserOpenAccount nowAccount = userOpenAccountDAO.queryByOpenId(session.getOpenId(), session.getChannelType());

		//如果当前账户不存在
		if(nowAccount == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}
		
		
		
		UserOpenAccount mobileAccount = userOpenAccountDAO.queryByOpenId(mobile, ChannelType.CHANNEL_MOBILE);

		// 手机没有被注册直接报错
		if (mobileAccount == null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_NOT_BIND.getCode());
		}

		// 如果保留手机账号
		if (persistMobileAccount) {

			// 修改第三方绑定账号的uid为mobile的uid
			userOpenAccountDAO.updateToBindUid(session.getOpenId(), session.getChannelType(), mobileAccount.getUid());


			//重建会话,  用手机的uid创建会话
			sessionHolder.createOrUpdateSession(mobileAccount.getUid(), LoginTypeEnum.SOURCE_TYPE_CLIENT.getCode(),session.getOpenId(), session.getChannelType());
			
			
		} else {

			//修改mobile的uid为 原来登录的uid
			userOpenAccountDAO.updateToBindUid(mobile, ChannelType.CHANNEL_MOBILE, nowAccount.getUid());
		}

	}

	@Override
	public RegisterResponseVo openIdRegister(OpenApiRegisterRequestVo vo) {
		
		
		
		
		return null;
	}

}