package com.zhuanquan.app.server.cache.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;
import com.zhuanquan.app.dal.dao.work.WorkSourceTypeDefineDAO;
import com.zhuanquan.app.server.cache.WorkSourceTypeDefineCache;


@Repository
public class WorkSourceTypeDefineCacheImpl extends CacheChangedListener implements WorkSourceTypeDefineCache {

	
	@Resource
	private RedisHelper redisHelper;
	
	
	@Resource
	private WorkSourceTypeDefineDAO  workSourceTypeDefineDAO;
	
	
	@Override
	public Map<String, WorkSourceTypeDefine> batchQueryTypes(List<String> sourceTypes) {
		
		
		String key = RedisKeyBuilder.getWorkSourceTypeDefineKey();
		
		
		
		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(key);
		
		if(hasKey) {
			
			List<String> result = redisHelper.hashMultiGet(key, sourceTypes);
			
			if(CollectionUtils.isEmpty(result)) {
				return null;
			}
			
			Map<String, WorkSourceTypeDefine> resultMap = new HashMap<String, WorkSourceTypeDefine>();
			
			for(String val:result) {
				
				WorkSourceTypeDefine record = JSON.parseObject(val, WorkSourceTypeDefine.class);
				
				resultMap.put(record.getSourceType(), record);
				
			}
			
			return resultMap;
			
		}
		
		

		
		List<WorkSourceTypeDefine> allList = workSourceTypeDefineDAO.queryAll();
		
		if(CollectionUtils.isEmpty(allList)) {
			return null;
		}
		
		Map<String, WorkSourceTypeDefine> resultMap = new HashMap<String, WorkSourceTypeDefine>();

		Map<String,String> pushMap  = new HashMap<String,String>();
		
		for(WorkSourceTypeDefine record:allList) {
			
			pushMap.put(record.getSourceType(), JSON.toJSONString(record));
			
			if(sourceTypes.contains(record.getSourceType())) {
				resultMap.put(record.getSourceType(), record);
			}
			
		}
		
		redisHelper.hashPutAll(key, pushMap);
		redisHelper.expire(key, 30, TimeUnit.MINUTES);

		return resultMap;
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