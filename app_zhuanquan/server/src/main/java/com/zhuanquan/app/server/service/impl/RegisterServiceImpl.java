package com.zhuanquan.app.server.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.constants.RegisterFlowConstants;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.utils.PhoneValidateUtils;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.server.cache.UserOpenAccountCache;
import com.zhuanquan.app.server.service.RegisterService;
import com.zhuanquan.app.server.service.TransactionService;

@Service
public class RegisterServiceImpl implements RegisterService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@Resource
	private AuthorBaseDAO authorBaseDAO;

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;
	
	@Resource
	private UserOpenAccountCache userOpenAccountCache;

	@Override
	public RegisterResponseVo mobileRegister(RegisterRequestVo vo) {

		// 基本参数校验
		validateBaseParam(vo);

		// 短信验证码校验
		validateVerifyCode(vo.getProfile(), vo.getVerifyCode(),
				RedisKeyBuilder.getRegisterSmsVerfiyCodeKey(vo.getProfile()));

		RegisterResponseVo response = transactionService.registerMobile(vo);

		sessionHolder.createOrUpdateSession(response.getUid(), vo.getLoginType(), vo.getProfile(),
				LoginType.CHANNEL_MOBILE, UserOpenAccount.NORMAL_ACCOUNT);

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
		validateMobilePassword(vo.getPassword());

	}

	/**
	 * 验证密码是否合法
	 * 
	 * @param password
	 */
	private void validateMobilePassword(String password) {
		// 密码不能为空
		if (StringUtils.isBlank(password)) {
			throw new BizException(BizErrorCode.EX_PWD_NOT_BE_EMPTY.getCode());
		}

		String reg = "^[a-zA-Z0-9~!@#$%^&*()-_+=<,>./?;:\"'{\\[}\\]\\|]{6,20}$";

		boolean success = password.matches(reg);

		if (!success) {
			throw new BizException(BizErrorCode.EX_PWD_IS_NOT_ILLEGLE.getCode());
		}
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
		validateVerifyCode(mobile, verifycode, RedisKeyBuilder.getBindMobileSmsVerifyCodeKey(mobile));

		UserOpenAccount account = userOpenAccountCache.queryByOpenId(mobile, LoginType.CHANNEL_MOBILE);

		// 已经被注册了直接报异常
		if (account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		// account创建
		account = UserOpenAccount.createMobileAccount(mobile, password, uid);

		userOpenAccountDAO.insertUserOpenAccount(account);

	}

	@Override
	public void mergeMobileAccount(long uid, String mobile, String verifycode, boolean persistMobileAccount) {

		// 短信验证码不为空
		if (StringUtils.isEmpty(verifycode)) {
			throw new BizException(BizErrorCode.EX_VERIFY_CODE_EMPTY.getCode());
		}

		// 验证手机是否合法的号码
		PhoneValidateUtils.isPhoneLegal(mobile);

		// 校验短信验证码是否正确
		validateVerifyCode(mobile, verifycode, RedisKeyBuilder.getBindMobileSmsVerifyCodeKey(mobile));

		UserSession session = SessionHolder.getCurrentLoginUserSession();

		// 判断传入账户的uid与当前登录的用户uid是否一致
		if (session == null || uid != session.getUid()) {
			throw new BizException(BizErrorCode.EX_UID_NOT_CURRENT_LOGIN_USER.getCode());
		}

		//
		UserOpenAccount nowAccount = userOpenAccountDAO.queryByOpenId(session.getOpenId(), session.getChannelType());

		// 如果当前账户不存在
		if (nowAccount == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		UserOpenAccount mobileAccount = userOpenAccountDAO.queryByOpenId(mobile, LoginType.CHANNEL_MOBILE);

		// 手机没有被注册直接报错
		if (mobileAccount == null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_NOT_BIND.getCode());
		}

		// 如果保留手机账号
		if (persistMobileAccount) {

			//清理第三方的缓存
			userOpenAccountCache.clearUserOpenAccountCache(session.getOpenId(), session.getChannelType());
			
			// 修改第三方绑定账号的uid为mobile的uid
			userOpenAccountDAO.updateToBindUid(session.getOpenId(), session.getChannelType(), mobileAccount.getUid());

			// 重建会话, 用手机的uid创建会话
			sessionHolder.createOrUpdateSession(mobileAccount.getUid(), LoginType.CHANNEL_MOBILE,
					session.getOpenId(), session.getChannelType(), mobileAccount.getIsVip());

		} else {

			//清理手机账户的缓存
			userOpenAccountCache.clearUserOpenAccountCache(mobile, LoginType.CHANNEL_MOBILE);

			
			// 修改mobile的uid为 原来登录的uid
			userOpenAccountDAO.updateToBindUid(mobile, LoginType.CHANNEL_MOBILE, nowAccount.getUid());
			

		}

	}



	@Override
	public void setNickNameOnRegisterStep1(UserSession session, String nickName) {

		if (StringUtils.isEmpty(nickName)) {
			throw new BizException(BizErrorCode.EX_UID_NICK_NAME_CAN_NOT_BE_NULL.getCode());
		}

		UserProfile profile = userProfileDAO.queryById(session.getUid());

		if (profile == null || profile.getRegStat() != RegisterFlowConstants.REG_STEP_CHOOSE_NICK_NAME) {
			logger.info("setNickNameOnRegisterStep1:[uid]=" + session.getUid() + ",[nickName]=" + nickName
					+ ",[profile]:" + profile == null ? "null" : JSON.toJSONString(profile));
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		// check nickname 如果和当前的一致，忽略
		if (profile.getNickName().equals(nickName.trim())) {
			return;
		}

		//
		
		AuthorBase base = authorBaseDAO.queryByAuthorId(profile.getAuthorId());
		
		// 如果是大v用户，并且设置的nickname和作者的名字一样的，那么不做校验默认允许重复
		if (base != null && base.getAuthorName().equals(nickName)) {
			// 更新昵称
			userProfileDAO.updateNickName(session.getUid(), nickName);
			return;
		}

		boolean hasUsed = userProfileDAO.queryNickNameHasBeenUsed(nickName);
		// 不允许和其他普通用户重复
		if (hasUsed) {
			throw new BizException(BizErrorCode.EX_UID_NICK_NAME_CAN_NOT_BE_DUPLICATE_WITH_PROFILE.getCode());
		}

		List<AuthorBase> list = authorBaseDAO.queryByAuthorName(nickName);
		// 不允许设置成和作者名一样的昵称
		if (CollectionUtils.isNotEmpty(list)) {
			throw new BizException(BizErrorCode.EX_UID_NICK_NAME_CAN_NOT_BE_DUPLICATE_WITH_AUTHORNAME.getCode());

		}

		// 更新昵称
		userProfileDAO.updateNickName(session.getUid(), nickName);

	}

	@Override
	public void setFollowTagOnRegisterStep2(UserSession session, List<Long> topicTags, List<Long> workCategries) {

		UserProfile profile = userProfileDAO.queryById(session.getUid());

		if (profile == null || profile.getRegStat() != RegisterFlowConstants.REG_STEP_CHOOSE_TAG) {
			logger.info("setFollowTagOnRegisterStep2:[uid]=" + session.getUid() + ",[topicTags]="
					+ JSON.toJSONString(topicTags) + ",[workCategries]:" + JSON.toJSONString(workCategries)
					+ ",[profile]:" + profile == null ? "null" : JSON.toJSONString(profile));
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		//

		transactionService.setFollowTagOnRegisterStep2(session.getUid(), topicTags, workCategries);
//		
//		// 完成第二步，设置状态为第三步等待执行
//		userProfileDAO.updateRegisterStatus(session.getUid(), UserProfile.REG_STAT_BEFORE_STEP3);

	}

	@Override
	public void setFollowAuthorsOnRegisterStep3(UserSession session, List<Long> authorIds) {

		UserProfile profile = userProfileDAO.queryById(session.getUid());

		if (profile == null || profile.getRegStat() != RegisterFlowConstants.REG_STEP_CHOOSE_FOLLOW_AUTHOR) {
			logger.info("setFollowTagOnRegisterStep3:[uid]=" + session.getUid() + ",[authorIds]="
					+ JSON.toJSONString(authorIds) + ",[profile]:" + profile == null ? "null"
							: JSON.toJSONString(profile));
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		// 第三步注册完了，设置状态为normal
		transactionService.setFollowAuthorsOnRegisterStep3(session.getUid(), authorIds);

	}

	@Override
	public void forgetPassword(String mobile, String verifyCode, String password) {

		// 短信验证码
		validateVerifyCode(mobile, verifyCode, RedisKeyBuilder.getForgetPwdSmsVerifyCodeKey(mobile));

		UserOpenAccount account = userOpenAccountCache.queryByOpenId(mobile, LoginType.CHANNEL_MOBILE);

		// 手机没有绑定注册
		if (account == null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_NOT_BIND.getCode());
		}

		userOpenAccountDAO.modifyPassword(mobile, password);

	}

	@Override
	public void modifyPassword(String verifyCode, String newPwd) {
		// 新密码是否合法
		validateMobilePassword(newPwd);

		// session
		UserSession session = SessionHolder.getCurrentLoginUserSession();

		String mobile = null;

		// 查看当前的登录方式不是手机登录的，手机登录才允许修改手机登录的密码
		if (session.getChannelType() == LoginType.CHANNEL_MOBILE) {
			mobile = session.getOpenId();
		} else {

			UserOpenAccount account = userOpenAccountDAO.queryByUidAndChannelType(session.getUid(),
					LoginType.CHANNEL_MOBILE);

			// 手机没有绑定注册
			if (account == null) {
				throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_NOT_BIND.getCode());
			}

			mobile = account.getOpenId();
		}

		// 短信验证码校验
		validateVerifyCode(mobile, verifyCode, RedisKeyBuilder.getModifyPwdSmsVerifyCodeKey(mobile));

		// 修改密码
		userOpenAccountDAO.modifyPassword(mobile, newPwd);
	}

	/**
	 * 短信验证码校验
	 * 
	 * @param mobile
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @param redisKey
	 *            短信验证码的key
	 */
	private void validateVerifyCode(String mobile, String verifyCode, String redisKey) {

		if (StringUtils.isEmpty(verifyCode)) {
			throw new BizException(BizErrorCode.EX_VERIFY_CODE_EMPTY.getCode());
		}

		String code = redisHelper.valueGet(redisKey);

		if (!verifyCode.equals(code)) {
			logger.warn("forgetPassword:[mobile]=" + mobile + ",[verifyCode]=" + verifyCode
					+ ",while code from redis is:" + code);
			throw new BizException(BizErrorCode.EX_VERIFY_CODE_ERR.getCode());
		}

	}

	@Override
	public int beforeBindCheck(String mobile) {
		
		//检查手机是否合法
		PhoneValidateUtils.isPhoneLegal(mobile);
		
		UserOpenAccount account = userOpenAccountCache.queryByOpenId(mobile, LoginType.CHANNEL_MOBILE);
		
		return account == null?0:1;
	}

}