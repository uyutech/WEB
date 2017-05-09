package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;

import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.author.TagDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.server.cache.UserOpenAccountCache;
import com.zhuanquan.app.server.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserProfileDAO userProfileDAO;

	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;

	@Resource
	private TagDAO tagDAO;

	@Resource
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;

	@Resource
	private UserOpenAccountCache userOpenAccountCache;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RegisterResponseVo registerMobile(RegisterRequestVo vo) {

		//
		UserOpenAccount account = userOpenAccountCache.queryByOpenId(vo.getProfile(), LoginType.CHANNEL_MOBILE);

		// 手机号已注册
		if (account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		// uid创建
		UserProfile profile = UserProfile.createMobileRegisterRecord(vo);
		long uid = userProfileDAO.insertRecord(profile);

		// account创建
		account = UserOpenAccount.createMobileAccount(vo.getProfile(), vo.getPassword(), uid);
		userOpenAccountDAO.insertUserOpenAccount(account);

		RegisterResponseVo response = new RegisterResponseVo();

		response.setUid(uid);

		return response;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo) {

		// 创建normal account
		UserProfile profile = UserProfile.registerThirdLoginUser();

		long uid = userProfileDAO.insertRecord(profile);

		UserOpenAccount openAccount = UserOpenAccount.createNormalOpenAccount(vo.getOpenId(), vo.getToken(), uid,
				vo.getChannelType());

		userOpenAccountDAO.insertUserOpenAccount(openAccount);

		return profile;
	}

}