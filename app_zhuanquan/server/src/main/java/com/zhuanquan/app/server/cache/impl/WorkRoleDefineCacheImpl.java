package com.zhuanquan.app.server.cache.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.work.WorkRoleDefine;
import com.zhuanquan.app.dal.dao.work.WorkRoleDefineDAO;
import com.zhuanquan.app.server.cache.WorkRoleDefineCache;


/**
 * 
 * @author zhangjun
 *
 */

@Service
public class WorkRoleDefineCacheImpl extends CacheChangedListener implements WorkRoleDefineCache {

	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private WorkRoleDefineDAO workRoleDefineDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, WorkRoleDefine> batchQueryRoleDefine(List<String> roleCodes) {
		
		String key = RedisKeyBuilder.getAllWorkRoleDefineKey();
		
		
		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(key);

		if(hasKey) {
			
			List<String> values = redisHelper.hashMultiGet(key, roleCodes);

			if(CollectionUtils.isEmpty(values)) {
				return null;
			}
			
			Map<String, WorkRoleDefine> map = new HashMap<String, WorkRoleDefine>();
			
			for(String val:values) {
				
				if(StringUtils.isNotEmpty(val)) {
					
					WorkRoleDefine define = JSON.parseObject(val, WorkRoleDefine.class);
					map.put(define.getRoleCode(), define);
				}
				
			}
			
			return map;
			
		}
		
		
		List<WorkRoleDefine> allList = workRoleDefineDAO.queryAll();

		if(CollectionUtils.isEmpty(allList)) {
			return null;
		}
		
		
		
		Map<String, WorkRoleDefine> result = new HashMap<String, WorkRoleDefine>();

		Map<String,String> pushMap = new HashMap<String,String>();
		
		
		for(WorkRoleDefine record:allList) {
			
			pushMap.put(record.getRoleCode(), JSON.toJSONString(record));
			
			if(roleCodes.contains(record.getRoleCode())) {
				result.put(record.getRoleCode(), record);
			}
			
		}
		
		
		if(MapUtils.isNotEmpty(pushMap)){
			redisHelper.hashPutAll(key, pushMap);
			redisHelper.expire(key, 30, TimeUnit.MINUTES);
		}
		
		return result;
	}

	@Override
	public WorkRoleDefine queryRoleDefine(String roleCode) {
		Map<String, WorkRoleDefine> map = batchQueryRoleDefine(Lists.newArrayList(roleCode));
		
		if(MapUtils.isEmpty(map)) {
			return null;
		}
		
		return map.get(roleCode);
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