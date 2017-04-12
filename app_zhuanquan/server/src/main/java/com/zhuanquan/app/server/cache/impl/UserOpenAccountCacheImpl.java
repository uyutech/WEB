package com.zhuanquan.app.server.cache.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.server.cache.UserOpenAccountCache;

@Service
public class UserOpenAccountCacheImpl implements UserOpenAccountCache {

	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;
	
	@Resource
	private RedisHelper redisHelper;
	
	@Override
	public UserOpenAccount queryByOpenId(String openId, int channelType) {
		
		String key = RedisKeyBuilder.buildUserOpenAccountKey(openId, channelType);
		
		String obj = redisHelper.valueGet(key);
		
		//不为null，不为空
		if(StringUtils.isNotEmpty(obj)) {
			
			//不为null，值为nil，说明账户不存在，不重复查询，防止缓存穿透
			if(obj.equals("nil")) {
				return null;
			}
			
			return JSON.parseObject(obj, UserOpenAccount.class);
		}

		
		UserOpenAccount account = userOpenAccountDAO.queryByOpenId(openId, channelType);
		
		if(account!=null) {
			
		    redisHelper.valueSet(key, JSON.toJSONString(account), 3, TimeUnit.MINUTES);
		} else {
			//nil的超时时间设置短一点
		    redisHelper.valueSet(key, "nil", 1, TimeUnit.MINUTES);
		}
		
		
		return account;
	}

	@Override
	public void clearUserOpenAccountCache(String openId, int channelType) {
		
		String key = RedisKeyBuilder.buildUserOpenAccountKey(openId, channelType);

		redisHelper.delete(key);
		
	}
	
}