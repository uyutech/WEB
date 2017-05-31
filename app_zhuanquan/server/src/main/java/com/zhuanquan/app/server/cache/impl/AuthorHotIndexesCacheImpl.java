package com.zhuanquan.app.server.cache.impl;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.author.AuthorHotIndexes;
import com.zhuanquan.app.dal.dao.author.AuthorHotIndexesDAO;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;



@Service
public class AuthorHotIndexesCacheImpl extends CacheChangedListener implements AuthorHotIndexesCache {

	@Resource
	private AuthorHotIndexesDAO authorHotIndexesDAO; 
	
	@Resource
	private RedisHelper redisHelper;
	
	@Override
	public List<AuthorHotIndexes> getAuthorHotIndexTop100() {
		
		String key = RedisKeyBuilder.getGlobalHotAuthorKey();
		
		String obj = redisHelper.valueGet(key);
		
		if(obj!=null) {
			return JSON.parseArray(obj, AuthorHotIndexes.class);
		}
		
		//懒加载查询,3小时懒加载更新一次
		List<AuthorHotIndexes> list = authorHotIndexesDAO.queryHotTopN(100);
		
		if(CollectionUtils.isNotEmpty(list)) {
		    redisHelper.valueSet(key, JSON.toJSONString(list), 3, TimeUnit.HOURS);
		}
		
		return list;
	}

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {
		
		return Lists.newArrayList(RedisCacheEnum.REDIS_CACHE_AUTHOR_HOT_INDEXES);
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {

		
	}
	
}