package com.zhuanquan.app.server.cache.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.server.cache.WorksCache;


@Service
public class WorksCacheImpl  extends CacheChangedListener  implements WorksCache {


	
	@Override
	public WorkBase queryWorkById(long workId) {
		

		return null;
	}

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}