package com.zhuanquan.app.server.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.server.service.TransactionService;



@Service
public class TransactionServiceImpl implements TransactionService {

	@Resource
	private UserProfileDAO userProfileDAO;
	
	
	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;
	
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public RegisterResponseVo registerMobile(RegisterRequestVo vo) {
		
		//
		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(vo.getProfile(), ChannelType.CHANNEL_MOBILE);
		
		//手机号已注册
		if(account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		
		//uid创建
		UserProfile profile = UserProfile.createMobileRegisterRecord(vo);
		long uid = userProfileDAO.insertRecord(profile);

		//account创建
		account = UserOpenAccount.createMobileAccount(vo.getProfile(), vo.getPassword(), uid);
		userOpenAccountDAO.insertUserOpenAccount(account);
		
		
		
		RegisterResponseVo response = new RegisterResponseVo();

		response.setUid(uid);
//		response.setMobile(profile.getMobile());
//		response.setUserName(vo.getProfile());
//		response.setAllowAttation(profile.getAllowAttation());

		return response;
	}
	
}