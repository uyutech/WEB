package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;
import com.zhuanquan.app.dal.dao.author.TagDAO;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;
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
	
	@Resource
	private VipAuthorOpenAccountMappingDAO vipAuthorOpenAccountMappingDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long registerMobile(MobileRegisterRequestVo vo) {

		//
		UserOpenAccount account = userOpenAccountCache.queryByOpenId(vo.getMobile(), LoginType.CHANNEL_MOBILE);

		// 手机号已注册
		if (account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		// uid创建
		UserProfile profile = UserProfile.createMobileRegisterRecord(vo);
		long uid = userProfileDAO.insertRecord(profile);

		// account创建
		account = UserOpenAccount.createMobileAccount(vo.getMobile(), vo.getPassword(), uid);
		userOpenAccountDAO.insertUserOpenAccount(account);

		return uid;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo) {

		
		//vip校验
		VipAuthorOpenAccountMapping record = vipAuthorOpenAccountMappingDAO.queryRecordByOpenId(vo.getOpenId(), vo.getChannelType());
		
		UserProfile profile = null;
		//非大v用户/没有录入作者信息
		if(record == null || record.getAuthorId() == null) {
			
			profile = UserProfile.registerNormalThirdLoginUser();
			
		} else {
			profile = UserProfile.registerVipThirdLoginUser(record.getAuthorId());
		}

		long uid = userProfileDAO.insertRecord(profile);

		UserOpenAccount openAccount = UserOpenAccount.createNormalOpenAccount(vo.getOpenId(), vo.getToken(), uid,
				vo.getChannelType());

		userOpenAccountDAO.insertUserOpenAccount(openAccount);

		return profile;
	}

}