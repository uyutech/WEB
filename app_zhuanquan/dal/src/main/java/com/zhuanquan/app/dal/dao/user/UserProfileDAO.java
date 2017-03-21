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
	
	
}