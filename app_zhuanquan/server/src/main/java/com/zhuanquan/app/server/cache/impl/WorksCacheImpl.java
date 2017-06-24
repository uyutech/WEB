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
import com.zhuanquan.app.common.constants.WorkRoleTypeConstants;
import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkBaseExtend;
import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.dal.dao.work.WorkAttenderDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseExtendDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceExtendDAO;
import com.zhuanquan.app.dal.dao.work.WorkHotIndexDAO;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.WorksCache;

@Service
public class WorksCacheImpl extends CacheChangedListener implements WorksCache {

	@Resource
	private WorkBaseDAO workBaseDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private WorkTagMappingDAO workTagMappingDAO;

	@Resource
	private WorkBaseExtendDAO workBaseExtendDAO;

	@Resource
	private WorkAttenderDAO workAttenderDAO;

	@Resource
	private WorkContentSourceDAO workContentSourceDAO;

	@Resource
	private WorkContentSourceExtendDAO workContentSourceExtendDAO;

	@Resource
	private WorkHotIndexDAO workHotIndexDAO;

	@Resource
	private AuthorCache authorCache;

	@Override
	public WorkBase queryWorkById(long workId) {

		return lazyFetchWorkBaseCache(workId);
	}

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {

		return null;
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {

	}

	/**
	 * 懒加载cache,缓存30分钟
	 * 
	 * @param workId
	 */
	@Override
	public WorkBase lazyFetchWorkBaseCache(long workId) {

		String key = RedisKeyBuilder.getWorkBaseCacheKey(workId);

		String obj = redisHelper.valueGet(key);

		if (StringUtils.isNotEmpty(obj)) {
			return JSON.parseObject(obj, WorkBase.class);
		}

		WorkBase baseInfo = workBaseDAO.queryWorkById(workId);

		if (baseInfo == null) {
			return null;
		}
		redisHelper.valueSet(key, JSON.toJSONString(baseInfo), 30, TimeUnit.MINUTES);

		return baseInfo;
	}

	/**
	 * 获取作品的tag
	 * 
	 * @param workId
	 * @return
	 */
	@Override
	public List<WorkTagMapping> lazyFetchWorkTags(long workId) {

		String key = RedisKeyBuilder.getWorkTagsCacheKey(workId);

		String obj = redisHelper.valueGet(key);

		if (StringUtils.isNotEmpty(obj)) {
			return JSON.parseArray(obj, WorkTagMapping.class);
		}

		List<WorkTagMapping> list = workTagMappingDAO.queryWorkTags(workId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		redisHelper.valueSet(key, JSON.toJSONString(list), 30, TimeUnit.MINUTES);

		return list;
	}

	/**
	 * 获取扩展属性
	 * 
	 * @return
	 */
	@Override
	public Map<String, List<WorkBaseExtend>> lazyFetchWorkBaseExtendInfo(long workId) {

		String key = RedisKeyBuilder.getWorkBaseExtendCacheKey(workId);

		// key为 属性id，value为 list结构的WorkBaseExtend
		Map<String, String> resultMap = redisHelper.hashGetByKey(key);

		if (resultMap != null) {

			Map<String, List<WorkBaseExtend>> map = new HashMap<String, List<WorkBaseExtend>>();

			for (Entry<String, String> entry : resultMap.entrySet()) {
				map.put(entry.getKey(), JSON.parseArray(entry.getValue(), WorkBaseExtend.class));
			}

			return map;
		}

		return lazyInitWorkBaseExtendCache(workId);

	}

	// 懒加载
	private Map<String, List<WorkBaseExtend>> lazyInitWorkBaseExtendCache(long workId) {

		String key = RedisKeyBuilder.getWorkBaseExtendCacheKey(workId);

		List<WorkBaseExtend> list = workBaseExtendDAO.queryExtendInfoByWorkId(workId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<String, List<WorkBaseExtend>> result = new HashMap<String, List<WorkBaseExtend>>();

		for (WorkBaseExtend extend : list) {

			List<WorkBaseExtend> temp = result.get(extend.getExtendAttr().toString());

			if (temp == null) {
				result.put(extend.getExtendAttr().toString(), Lists.newArrayList(extend));
			} else {
				temp.add(extend);
			}
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, List<WorkBaseExtend>> entry : result.entrySet()) {
			map.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// 放到缓存
		redisHelper.hashPutAll(key, map);

		return result;
	}

	/**
	 * 
	 * @param workId
	 * @return key手sourceid，value是 list。策划，出品人这些的sourceid都为0
	 */
	@Override
	public Map<String, List<WorkAttender>> lazyFetchWorkAttenderCache(long workId) {

		String key = RedisKeyBuilder.getWorkAttenderCacheKey(workId);

		// key为 属性id，value为 list结构的WorkAttender
		Map<String, String> resultMap = redisHelper.hashGetByKey(key);

		if (resultMap != null) {

			Map<String, List<WorkAttender>> map = new HashMap<String, List<WorkAttender>>();

			for (Entry<String, String> entry : resultMap.entrySet()) {
				map.put(entry.getKey(), JSON.parseArray(entry.getValue(), WorkAttender.class));
			}

			return map;
		}

		return lazyInitWorkAttenderCache(workId);

	}

	/**
	 * 懒记载
	 * 
	 * @param workId
	 * @return
	 */
	private Map<String, List<WorkAttender>> lazyInitWorkAttenderCache(long workId) {

		String key = RedisKeyBuilder.getWorkAttenderCacheKey(workId);

		List<WorkAttender> list = workAttenderDAO.queryWorkAttender(workId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<String, List<WorkAttender>> result = new HashMap<String, List<WorkAttender>>();

		for (WorkAttender record : list) {

			List<WorkAttender> temp = result.get(record.getMediaSourceId().toString());

			if (temp == null) {
				result.put(record.getMediaSourceId().toString(), Lists.newArrayList(record));
			} else {
				temp.add(record);
			}
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, List<WorkAttender>> entry : result.entrySet()) {
			map.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// 放到缓存
		redisHelper.hashPutAll(key, map);

		return result;

	}

	@Override
	public Map<String, List<WorkContentSource>> lazyFetchWorkContentSourceCache(long workId) {

		String key = RedisKeyBuilder.getWorkContentSourceCacheKey(workId);

		// key为 属性id，value为 list结构的WorkContentSource
		Map<String, String> resultMap = redisHelper.hashGetByKey(key);

		if (resultMap != null) {

			Map<String, List<WorkContentSource>> map = new HashMap<String, List<WorkContentSource>>();

			for (Entry<String, String> entry : resultMap.entrySet()) {
				map.put(entry.getKey(), JSON.parseArray(entry.getValue(), WorkContentSource.class));
			}

			return map;
		}

		return lazyInitWorkContentSourceCache(workId);

	}

	/**
	 * 懒记载内容资源的cache
	 * 
	 * @param workId
	 * @return
	 */
	private Map<String, List<WorkContentSource>> lazyInitWorkContentSourceCache(long workId) {

		String key = RedisKeyBuilder.getWorkContentSourceCacheKey(workId);

		List<WorkContentSource> list = workContentSourceDAO.queryByWorkId(workId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<String, List<WorkContentSource>> result = new HashMap<String, List<WorkContentSource>>();

		for (WorkContentSource record : list) {

			List<WorkContentSource> temp = result.get(record.getSourceCategory().toString());

			if (temp == null) {
				result.put(record.getSourceCategory().toString(), Lists.newArrayList(record));
			} else {
				temp.add(record);
			}
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, List<WorkContentSource>> entry : result.entrySet()) {
			map.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// 放到缓存
		redisHelper.hashPutAll(key, map);

		return result;

	}

	/**
	 * 获取多媒体资源的扩展信息
	 * 
	 * @param sourceId
	 * @return
	 */
	@Override
	public Map<String, List<WorkContentSourceExtend>> lazyFetchWorkContentSourceExtendCache(long sourceId) {

		String key = RedisKeyBuilder.getWorkContentSourceExtendCacheKey(sourceId);

		// key为 属性id，value为 list结构的WorkContentSource
		Map<String, String> resultMap = redisHelper.hashGetByKey(key);

		if (resultMap != null) {

			Map<String, List<WorkContentSourceExtend>> map = new HashMap<String, List<WorkContentSourceExtend>>();

			for (Entry<String, String> entry : resultMap.entrySet()) {
				map.put(entry.getKey(), JSON.parseArray(entry.getValue(), WorkContentSourceExtend.class));
			}

			return map;
		}

		return lazyInitWorkContentSourceExtendCache(sourceId);

	}

	/**
	 * 懒记载内容资源的cache
	 * 
	 * @param workId
	 * @return
	 */
	private Map<String, List<WorkContentSourceExtend>> lazyInitWorkContentSourceExtendCache(long sourceId) {

		String key = RedisKeyBuilder.getWorkContentSourceExtendCacheKey(sourceId);

		List<WorkContentSourceExtend> list = workContentSourceExtendDAO.queryBySourceId(sourceId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<String, List<WorkContentSourceExtend>> result = new HashMap<String, List<WorkContentSourceExtend>>();

		for (WorkContentSourceExtend record : list) {

			List<WorkContentSourceExtend> temp = result.get(record.getExtendAttr().toString());

			if (temp == null) {
				result.put(record.getExtendAttr().toString(), Lists.newArrayList(record));
			} else {
				temp.add(record);
			}
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, List<WorkContentSourceExtend>> entry : result.entrySet()) {
			map.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// 放到缓存
		redisHelper.hashPutAll(key, map);

		return result;
	}

	@Override
	public List<DiscoveryHotWorkVo> queryDiscoverHotWorksByPage(DiscoveryPageQueryRequest request) {

		String hotWorkKey = RedisKeyBuilder.getDiscoverHotWorkKey();

		// 尝试从zset缓存中获取
		Set<String> sets = redisHelper.zsetRevrange(hotWorkKey, request.getFromIndex(), request.getFromIndex() + request.getLimit() - 1);

		// 缓存中有值
		if (sets != null && sets.size() != 0) {

			return CommonUtil.deserializArray(sets, DiscoveryHotWorkVo.class);
		}

		List<WorkHotIndex> works = workHotIndexDAO.queryTopN(100);

		if (CollectionUtils.isEmpty(works)) {
			return null;
		}

		List<DiscoveryHotWorkVo> resultList = new ArrayList<DiscoveryHotWorkVo>();

		for (WorkHotIndex index : works) {
			DiscoveryHotWorkVo vo = new DiscoveryHotWorkVo();

			WorkBase base = this.lazyFetchWorkBaseCache(index.getWorkId());
			vo.setScore(index.getScore());
			vo.setSubject(base.getSubject());
			vo.setWorkId(index.getWorkId());

			// 出品人信息
			vo.setAuthorInfo(fetchProductorInfo(index.getWorkId()));

			resultList.add(vo);
		}

		// 设置个人的缓存，有效期为5分钟

		Set<TypedTuple<String>> set = new LinkedHashSet<TypedTuple<String>>(resultList.size());

		// zset按照score排序，即按照热度排序
		for (int index = 0; index < resultList.size(); index++) {
			DiscoveryHotWorkVo vo = resultList.get(index);
			set.add(new DefaultTypedTuple(JSON.toJSONString(vo), (double) vo.getScore()));
		}

		redisHelper.zsetAdd(hotWorkKey, set);
		redisHelper.expire(hotWorkKey, 5, TimeUnit.MINUTES);

		return resultList;
	}

	/**
	 * 获取出品人信息
	 * 
	 * @param workId
	 * @return
	 */
	private String fetchProductorInfo(long workId) {

		Map<String, List<WorkAttender>> attenderMap = this.lazyFetchWorkAttenderCache(workId);

		if (MapUtils.isNotEmpty(attenderMap)) {
			// 获取出品人信息

			List<WorkAttender> attenders = attenderMap.get(0);

			List<Long> productorIds = new ArrayList<>();
			for (WorkAttender attender : attenders) {
				// 出品人
				if (attender.getRoleType() == WorkRoleTypeConstants.WORK_ROLE_PRODUCTOR) {
					productorIds.add(attender.getAuthorId());
				}
			}

			StringBuilder productor = new StringBuilder();
			Map<String, AuthorBaseInfoBo> authorMap = authorCache.batchQueryAuthorBaseByIds(productorIds);

			boolean isFirst = true;
			for (Entry<String, AuthorBaseInfoBo> entry : authorMap.entrySet()) {

				if (isFirst) {
					productor.append(entry.getValue().getAuthorName());
					isFirst = false;
				} else {
					productor.append("/").append(entry.getValue().getAuthorName());

				}

			}

			return productor.toString();

		}

		return null;

	}

}