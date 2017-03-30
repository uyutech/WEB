package com.zhuanquan.app.dal.dao.user;

import com.zhuanquan.app.dal.model.user.UserProfile;

public interface UserProfileDAO {
	
	/**
	 * 根据uid查询
	 * 
	 * @param uid
	 * @return
	 */
	UserProfile queryById(long uid);
	
	
	/**
	 * 插入记录
	 * @param profile
	 * @return
	 */
	long insertRecord(UserProfile profile);
	
//	
//	/**
//	 * 根据用户名查询
//	 * @param userName
//	 * @return
//	 */
//	UserProfile queryByUserName(String userName);
	
//	
//	/**
//	 * 绑定手机号
//	 * @param uid
//	 * @param mobile
//	 */
//	void bindMobile(long uid,String mobile);
}