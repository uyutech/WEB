package com.zhuanquan.app.server.cache.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.common.model.author.AuthorExtendInfo;
import com.zhuanquan.app.common.model.author.AuthorHotIndexes;
import com.zhuanquan.app.common.model.author.AuthorTagMapping;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.dal.dao.author.AuthorExtendInfoDAO;
import com.zhuanquan.app.dal.dao.author.AuthorHotIndexesDAO;
import com.zhuanquan.app.dal.dao.author.AuthorTagMappingDAO;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformDefineDAO;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformInfoDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;
import com.zhuanquan.app.server.cache.TagCache;


@Service
public class AuthorCacheImpl extends CacheChangedListener implements AuthorCache {

	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private AuthorBaseDAO authorBaseDAO;
	
	@Resource
	private AuthorTagMappingDAO authorTagMappingDAO;
	
	@Resource
	private TagCache tagCache;
	
	@Resource
	private AuthorExtendInfoDAO authorExtendInfoDAO;
	
	@Resource
	private AuthorHotIndexesDAO authorHotIndexesDAO;
	
	@Resource
	private AuthorHotIndexesCache authorHotIndexesCache;
	
	
	@Resource
	private AuthorThirdPlatformDefineDAO authorThirdPlatformDefineDAO;
	
	@Resource
	private AuthorThirdPlatformInfoDAO authorThirdPlatformInfoDAO;
	
	

	@Override
	public AuthorBaseInfoBo queryAuthorBaseById(long authorId) {
		Map<String, AuthorBaseInfoBo> map = batchQueryAuthorBaseByIds(Lists.newArrayList(authorId))	;	
		
		if(MapUtils.isEmpty(map)) {
			return null;
		}
		
		return map.get(authorId+"");
	}
	
	
	
	
	@Override
	public Map<String, AuthorBaseInfoBo> batchQueryAuthorBaseByIds(List<Long> authorIds) {
		
	if (CollectionUtils.isEmpty(authorIds)) {
		return null;
	}
	
	String key = RedisKeyBuilder.getAuthorBaseKey();
	
	List<String> authorIdsHashKey = new ArrayList<String>();
	
	for (Long id : authorIds) {
		authorIdsHashKey.add(id.toString());
    }
	
	
	List<String> authorBaseList = redisHelper.hashMultiGet(key, authorIdsHashKey);
	
	
	List<Long> exclude = new ArrayList<Long>();
	
	
	Map<String, AuthorBaseInfoBo> resultMap = new HashMap<String, AuthorBaseInfoBo>();
	
		for (int index = 0; index < authorIdsHashKey.size(); index++) {

			String baseStr = authorBaseList.get(index);

			// 表示redis没有，需要懒加载进去
			if (StringUtils.isEmpty(baseStr)) {
				exclude.add(authorIds.get(index));

				// 先设置为null
				resultMap.put(authorIdsHashKey.get(index), null);
			} else {
				resultMap.put(authorIdsHashKey.get(index), JSON.parseObject(baseStr, AuthorBaseInfoBo.class));
			}

		}
//
//		//
//
		//把缓存中缺少的通过这个方式补上去
		if (CollectionUtils.isNotEmpty(exclude)) {
			
			List<AuthorBase> list = authorBaseDAO.queryByAuthorIds(exclude);


			Map<String, String> needCommitMap = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(list)) {

				
				Map<String,Pair<String, String>> map = generateTagStrMap(exclude);
				
				for (AuthorBase base : list) {
					
					// 

					AuthorBaseInfoBo bo = new AuthorBaseInfoBo();
					bo.setAuthorId(base.getAuthorId());
					bo.setAuthorName(base.getAuthorName());
					bo.setHeadUrl(base.getHeadUrl());
					
					Pair<String, String> pair = map.get(base.getAuthorId().toString());
					if(pair!=null) {
					    bo.setTagDesc(pair.getLeft());
					    bo.setTagIds(pair.getRight());
					}
					
					resultMap.put(base.getAuthorId().toString(), bo);
					needCommitMap.put(base.getAuthorId().toString(), JSON.toJSONString(bo));
				}

				// 需要回补到缓存中的值
				if (MapUtils.isNotEmpty(needCommitMap)) {
					redisHelper.hashPutAll(key, needCommitMap);
					redisHelper.expire(key, 2, TimeUnit.HOURS);
				}

			}

		}


		return resultMap;
	}
	
	
	/**
	 * 
	 * @param authorIds
	 * @return
	 */
	private Map<String,Pair<String, String>> generateTagStrMap(List<Long> authorIds) {
		
		Map<String,Pair<String, String>> map = new HashMap<String,Pair<String, String>>();
		
		//order by  authorid，ordernum,create_time
		List<AuthorTagMapping> tagMapList =  authorTagMappingDAO.queryByAuthorIds(authorIds);

		if(CollectionUtils.isEmpty(tagMapList)) {
			return map;
		}
		
		
		
		for(AuthorTagMapping record:tagMapList) {
			
			long tagId = record.getTagId();
			
		     Tag tag = tagCache.getTagById(tagId);
		     if(tag == null){
		    	 continue;
		     }
			
		     
			Pair<String, String> pair = map.get(record.getAuthorId().toString());
			
			if(pair == null) {

			     map.put(record.getAuthorId().toString(), Pair.of(tag.getTagName(), tag.getTagId().toString()));
			     
			} else {

				String tagDesc = pair.getLeft()+","+tag.getTagName();
				
				String tagIdStr = pair.getRight()+","+tag.getTagId().toString();
				
				map.put(record.getAuthorId().toString(), Pair.of(tagDesc, tagIdStr));
			     
			}
		}
		

		return map;
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param authorId
	 * @return
	 */
	private AuthorBase fetchAuthorBaseCache(long authorId) {
		
		
		String key = RedisKeyBuilder.getAuthorBaseKey(authorId);

		String obj = redisHelper.valueGet(key);

		if (StringUtils.isNotEmpty(obj)) {
			return JSON.parseObject(obj, AuthorBase.class);
		}

		AuthorBase baseInfo = authorBaseDAO.queryByAuthorId(authorId);
		
		if (baseInfo == null) {
			return null;
		}
		redisHelper.valueSet(key, JSON.toJSONString(baseInfo), 30, TimeUnit.MINUTES);

		return baseInfo;
		
	}
	
	
	/**
	 * 
	 * @param authorId
	 * @return
	 */
	private Map<String, List<AuthorExtendInfo>> fetchAuthorExtendInfo(long authorId) {

		String key = RedisKeyBuilder.getAuthorBaseExtendKey(authorId);

		// key为 属性id，value为 list结构的WorkBaseExtend
		Map<String, String> resultMap = redisHelper.hashGetByKey(key);

		if (resultMap != null) {

			Map<String, List<AuthorExtendInfo>> map = new HashMap<String, List<AuthorExtendInfo>>();

			for (Entry<String, String> entry : resultMap.entrySet()) {
				map.put(entry.getKey(), JSON.parseArray(entry.getValue(), AuthorExtendInfo.class));
			}

			return map;
		}

		return lazyInitAuthorExtendCache(authorId);

	}

	// 懒加载
	private Map<String, List<AuthorExtendInfo>> lazyInitAuthorExtendCache(long authorId) {

		String key = RedisKeyBuilder.getAuthorBaseExtendKey(authorId);

		
		List<AuthorExtendInfo> list = authorExtendInfoDAO.queryByAuthorId(authorId);
		
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<String, List<AuthorExtendInfo>> result = new HashMap<String, List<AuthorExtendInfo>>();

		for (AuthorExtendInfo extend : list) {

			List<AuthorExtendInfo> temp = result.get(extend.getAttrType().toString());

			if (temp == null) {
				result.put(extend.getAttrType().toString(), Lists.newArrayList(extend));
			} else {
				temp.add(extend);
			}
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, List<AuthorExtendInfo>> entry : result.entrySet()) {
			map.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// 放到缓存
		redisHelper.hashPutAll(key, map);

		return result;
	}


	@Override
	public long queryAuthorHotIndex(long authorId) {
		
		String  key = RedisKeyBuilder.getAuthorHotIndexKey(authorId);
		
		//获取key的值
		String value = redisHelper.valueGet(key);
		
		if(value!=null) {
			return Long.parseLong(value);
		}
		
		
		AuthorHotIndexes record = authorHotIndexesDAO.queryByAuthorId(authorId);
		
		if(record == null) {
			return 0L;
		}

		redisHelper.valueSet(key, record.getScore().toString(), 3, TimeUnit.MINUTES);

		return record.getScore();
	}


	@Override
	public AuthorThirdPlatformDefine queryById(long platformId) {
		
		String key = RedisKeyBuilder.getPlatformDefineKey();
		
		
		String val = redisHelper.hashGet(key, platformId+"");
		
		if(StringUtils.isNotEmpty(val)) {
			
			return JSON.parseObject(val, AuthorThirdPlatformDefine.class);
		}
		
		List<AuthorThirdPlatformDefine> all = authorThirdPlatformDefineDAO.queryAllInfo();
		
		
		if(CollectionUtils.isEmpty(all)){
			return null;
		}
		
		
		AuthorThirdPlatformDefine target = null;
		
		Map<String,String> map = new HashMap<String,String>();
		
		for(AuthorThirdPlatformDefine record:all) {
			if(record.getId().longValue() ==platformId) {
				target = record;
			}
			
			map.put(record.getId().toString(), JSON.toJSONString(record));
		}
		
		
		redisHelper.hashPutAll(key, map);
		
		
		return target;
	}


	@Override
	public List<AuthorThirdPlatformInfo> queryAuthorOtherPlatformInfo(long authorId) {
		
		String key = RedisKeyBuilder.getAuthorOtherPlatformInfoKey(authorId);


		String val = redisHelper.valueGet(key);
		
		if(val != null) {
			return JSON.parseArray(val, AuthorThirdPlatformInfo.class);
		}
		
		List<AuthorThirdPlatformInfo> list = authorThirdPlatformInfoDAO.queryByAuthorId(authorId);
		
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		redisHelper.valueSet(key, JSON.toJSONString(list), 30, TimeUnit.MINUTES);
		
		return list;
	}


	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {
		return null;
	}


	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {
		
	}


	@Override
	public List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(DiscoveryPageQueryRequest request) {
		
		String hotKey = RedisKeyBuilder.getDiscoverHotAuthorKey();

		// 尝试从zset缓存中获取
		Set<String> sets = redisHelper.zsetRevrange(hotKey, request.getFromIndex(), request.getFromIndex() + request.getLimit() - 1);

		// 缓存中有值
		if (sets != null && sets.size() != 0) {

			return CommonUtil.deserializArray(sets, DiscoveryHotAuthorVo.class);
		}

		List<AuthorHotIndexes> authors = authorHotIndexesCache.getAuthorHotIndexTop100();

		if (CollectionUtils.isEmpty(authors)) {
			return null;
		}

		List<DiscoveryHotAuthorVo> resultList = new ArrayList<DiscoveryHotAuthorVo>();

		for (AuthorHotIndexes index : authors) {
			
			DiscoveryHotAuthorVo vo = new DiscoveryHotAuthorVo();

			AuthorBase base = this.fetchAuthorBaseCache(index.getAuthorId());
			
			vo.setAuthorId(base.getAuthorId());
			vo.setAuthorName(base.getAuthorName());
			vo.setHeadUrl(base.getHeadUrl());			
			vo.setScore(index.getScore());
			
			resultList.add(vo);
		}

		// 设置个人的缓存，有效期为5分钟

		Set<TypedTuple<String>> set = new LinkedHashSet<TypedTuple<String>>(resultList.size());

		// zset按照score排序，即按照热度排序
		for (int index = 0; index < resultList.size(); index++) {
			DiscoveryHotAuthorVo vo = resultList.get(index);
			set.add(new DefaultTypedTuple(JSON.toJSONString(vo), (double) vo.getScore()));
		}

		
		redisHelper.zsetAdd(hotKey, set);
		redisHelper.expire(hotKey, 5, TimeUnit.MINUTES);

		return resultList;
	}



	
}