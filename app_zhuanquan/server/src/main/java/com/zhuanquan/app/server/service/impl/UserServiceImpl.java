package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.dal.model.user.UserProfile;
import com.zhuanquan.app.server.service.UserService;

/**
 * user service
 * @author zhangjun
 *
 */

@Service
public class UserServiceImpl implements  UserService{

	@Resource
	private UserProfileDAO userProfileDAO;
	
	
	@Override
	public UserProfile queryUserProfileByUid(long uid) {
		return userProfileDAO.queryById(uid);
	}


	@Override
	public UserProfile queryUserProfileByMobile(String mobile) {
		
        
		return userProfileDAO.queryByUserName(mobile);
	}
	
	
	
}