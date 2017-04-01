package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.model.user.UserProfile;

/**
 * user service
 * @author zhangjun
 *
 */
public interface UserService {
	
	/**
	 * 根据uid查询信息
	 * @param uid
	 * @return
	 */
	UserProfile queryUserProfileByUid(long uid);
	
//	/**
//	 * 根据手机号查询，手机是否被注册了
//	 * @param mobile
//	 * @return
//	 */
//	UserProfile queryUserProfileByMobile(String mobile);
	
}