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
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;
import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryQuerySuggestTagRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestTagInfoVo;
import com.zhuanquan.app.dal.dao.common.SuggestSourceMgrDAO;
import com.zhuanquan.app.dal.dao.work.WorkSourceTypeDefineDAO;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;
import com.zhuanquan.app.server.cache.SuggestSourceMgrCache;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.cache.WorkSourceTypeDefineCache;

@Service
public class SuggestSourceMgrCacheImpl extends CacheChangedListener implements SuggestSourceMgrCache {

	@Resource
	private SuggestSourceMgrDAO suggestSourceMgrDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private WorkSourceTypeDefineCache workSourceTypeDefineCache;
	

	@Resource
	private WorkTagMappingDAO workTagMappingDAO;
	
	@Resource
	private WorkSourceTypeDefineDAO workSourceTypeDefineDAO;
	
	
	@Resource
	private TagCache tagCache;
	
	
	

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

	@Override
	public DiscoverySuggestTagInfoVo queryDiscoverSuggestTags(DiscoveryQuerySuggestTagRequest request) {
		
		//这里是可以做缓存的，节省时间，后续补充
		
		DiscoverySuggestTagInfoVo vo = new DiscoverySuggestTagInfoVo();
		vo.setFromIndex(request.getFromIndex());
		vo.setLimit(request.getLimit());
		
		
		
		//判断类型是否为空
		if(CollectionUtils.isEmpty(request.getSourceTypes())) {
			return vo;
		}
		
		List<String> typeList = workSourceTypeDefineDAO.querySourceTypeAndSubType(request.getSourceTypes());
		//

		List<Long> tagIds = workTagMappingDAO.queryTagIds(typeList, request.getFromIndex(), request.getLimit());
		
		if(CollectionUtils.isEmpty(tagIds)){
			return vo;
		}

		List<TagInfoBo> targetTagList = new ArrayList<>();
		for(Long tagId:tagIds) {
			Tag tag = tagCache.getTagById(tagId);
			
			if(tag!=null) {
				TagInfoBo bo  = new TagInfoBo();
				bo.setTagId(tagId);
				bo.setTagName(tag.getTagName());
				bo.setTagType(tag.getTagType());
				
				
				targetTagList.add(bo);
			}
		}

		vo.setTagList(targetTagList);

		return vo;
	}

}