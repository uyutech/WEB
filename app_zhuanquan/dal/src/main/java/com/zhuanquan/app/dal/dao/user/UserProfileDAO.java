package com.zhuanquan.app.dal.dao.user;

import com.zhuanquan.app.common.model.user.UserProfile;

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
	
	
	/**
	 * 查询昵称的数量
	 * @param nickName
	 * @return
	 */
	int queryCountByNickName(String nickName);
	
    /**
     * 修改nick name 在step1，同时更新profile的register状态
     * @param uid
     * @param nickName
     * @return
     */
	int updateNickNameOnStep1(long uid,String nickName);
	
	/**
	 * 更新注册的状态
	 * @param uid 用户id
	 * @param registerStatus 注册状态
	 * @return
	 */
	int updateRegisterStatus(long uid,int registerStatus);
	
}