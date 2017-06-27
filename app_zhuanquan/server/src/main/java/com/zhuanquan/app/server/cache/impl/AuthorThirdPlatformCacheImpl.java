package com.zhuanquan.app.server.cache.impl;

import java.util.ArrayList;
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
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformDefineDAO;
import com.zhuanquan.app.server.cache.AuthorThirdPlatformCache;

/**
 * 第三方平台的cahche
 * 
 * @author zhangjun
 *
 */
@Service
public class AuthorThirdPlatformCacheImpl extends CacheChangedListener implements AuthorThirdPlatformCache {

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private AuthorThirdPlatformDefineDAO authorThirdPlatformDefineDAO;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, AuthorThirdPlatformDefine> batchQueryThirdPlatformInfo(List<Integer> platformIds) {

		if (CollectionUtils.isEmpty(platformIds)) {
			return null;
		}

		//
		String key = RedisKeyBuilder.getAuthorThirdplatformInfoKey();

		List<String> platIdsHashKey = new ArrayList<String>();

		for (Integer id : platformIds) {
			platIdsHashKey.add(id.toString());
		}

		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(key);

		if (hasKey) {

			List<String> platInfoList = redisHelper.hashMultiGet(key, platIdsHashKey);

			// 不为空
			if (!CollectionUtils.isEmpty(platInfoList)) {

				Map<String, AuthorThirdPlatformDefine> result = new HashMap<>();
				for (String record : platInfoList) {

					if (StringUtils.isNotEmpty(record)) {
						AuthorThirdPlatformDefine info = JSON.parseObject(record, AuthorThirdPlatformDefine.class);
						result.put(info.getId().toString(), info);
					}
				}

				return result;
			}

		}
		List<AuthorThirdPlatformDefine> allInfo = authorThirdPlatformDefineDAO.queryAllInfo();

		if (CollectionUtils.isEmpty(allInfo)) {
			return null;
		}

		Map<String, String> pushMap = new HashMap<String, String>();

		Map<String, AuthorThirdPlatformDefine> resultMap = new HashMap<String, AuthorThirdPlatformDefine>();

		for (AuthorThirdPlatformDefine record : allInfo) {
			pushMap.put(record.getId().toString(), JSON.toJSONString(record));

			// 有需要查询的id
			if (platIdsHashKey.contains(record.getId().toString())) {
				resultMap.put(record.getId().toString(), record);
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

	@Override
	public AuthorThirdPlatformDefine queryThirdPlatformInfo(Integer platformId) {
		Map<String, AuthorThirdPlatformDefine> map = batchQueryThirdPlatformInfo(Lists.newArrayList(platformId));
		if(MapUtils.isEmpty(map)){
			return null;
		}

		return map.get(platformId.toString());
	}

}