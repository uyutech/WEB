package com.zhuanquan.app.server.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;
import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;
import com.zhuanquan.app.dal.dao.common.SuggestSourceMgrDAO;
import com.zhuanquan.app.server.cache.SuggestSourceMgrCache;
import com.zhuanquan.app.server.cache.WorkSourceTypeDefineCache;

@Service
public class SuggestSourceMgrCacheImpl extends CacheChangedListener implements SuggestSourceMgrCache {

	@Resource
	private SuggestSourceMgrDAO suggestSourceMgrDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private WorkSourceTypeDefineCache workSourceTypeDefineCache;

	@Override
	public List<WorkSourceTypeInfoBo> getDiscoverSuggestSourceType() {

		String key = RedisKeyBuilder.getDiscoverSuggestSourceTypesKey();

		String object = redisHelper.valueGet(key);

		if (StringUtils.isNotEmpty(object)) {
			return JSON.parseArray(object, WorkSourceTypeInfoBo.class);
		}

		List<String> sourceTypes = suggestSourceMgrDAO.queryCurrentSuggestRecord();

		if (CollectionUtils.isEmpty(sourceTypes)) {
			return null;
		}

		Map<String, WorkSourceTypeDefine> map = workSourceTypeDefineCache.batchQueryTypes(sourceTypes);

		if (MapUtils.isEmpty(map)) {
			return null;
		}

		List<WorkSourceTypeInfoBo> resultList = new ArrayList<WorkSourceTypeInfoBo>();

		for (Entry<String, WorkSourceTypeDefine> entry : map.entrySet()) {

			WorkSourceTypeDefine define = entry.getValue();

			WorkSourceTypeInfoBo bo = new WorkSourceTypeInfoBo();

			bo.setSourceType(define.getSourceType());
			bo.setTypeName(define.getTypeName());

			resultList.add(bo);

		}

		return resultList;
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