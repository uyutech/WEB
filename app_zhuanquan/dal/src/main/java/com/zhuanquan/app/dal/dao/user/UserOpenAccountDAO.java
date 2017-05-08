package com.zhuanquan.app.dal.dao.user;

import com.zhuanquan.app.common.model.user.UserOpenAccount;

/**
 * 
 * @author zhangjun
 *
 */
public interface UserOpenAccountDAO {
	
	/**
	 * 根据openid和channelType 查询
	 * @param userOpenId
	 * @param channelType
	 * @return
	 */
	UserOpenAccount queryByOpenId(String userOpenId,int channelType);
	
	
	/**
	 * 更新token信息
	 * @param userOpenId openid
	 * @param channelType 渠道类型
	 * @param token token
	 * @return
	 */
	int updateAccountToken(String userOpenId,int channelType,String token);
	
	/**
	 * 插入
	 * @param account
	 * @return
	 */
	long insertUserOpenAccount(UserOpenAccount account);
	
	/**
	 * 绑定新的uid
	 * @param userOpenId
	 * @param channelType
	 * @param newUid
	 * @return
	 */
	int updateToBindUid(String userOpenId,int channelType,long newUid);
	
	
	/**
	 * 更新到active状态 
	 * @param userOpenId
	 * @param channelType
	 * @return
	 */
	int updateToActiveStat(String userOpenId,int channelType);
	
	
	/**
	 * 修改手机登录密码
	 * @param mobile 手机号
	 * @param password 新密码
	 */
	void updateMobilePassword(String mobile,String password);
	
	
	/**
	 * 根据uid和channelType查询
	 * @param uid
	 * @param channelType
	 * @return
	 */
	UserOpenAccount queryByUidAndChannelType(long uid,int channelType);

	
	
}