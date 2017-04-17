package com.zhuanquan.app.server.cache;

import com.zhuanquan.app.common.model.user.UserOpenAccount;

/**
 * open account 缓存
 * @author zhangjun
 *
 */
public interface UserOpenAccountCache {
	
	
	/**
	 * 根据openid 和 channel type查询缓存
	 * @param openId
	 * @param channelType
	 * @return
	 */
	UserOpenAccount queryByOpenId(String openId,int channelType);
	
	/**
	 * 清理缓存
	 * @param openId
	 * @param channelType
	 */
	void clearUserOpenAccountCache(String openId,int channelType);
	

	
}