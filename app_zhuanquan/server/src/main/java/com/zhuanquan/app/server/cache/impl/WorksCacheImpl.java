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
import org.apache.http.util.Asserts;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.constants.WorkExtendAttrConstants;
import com.zhuanquan.app.common.constants.WorkRoleTypeConstants;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkBaseExtend;
import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.common.model.work.WorkInspiration;
import com.zhuanquan.app.common.model.work.WorkMilestone;
import com.zhuanquan.app.common.model.work.WorkRoleDefine;
import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkContentSourceBriefInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkMilestoneBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.common.view.vo.work.WorkAttenderRoleViewVo;
import com.zhuanquan.app.common.view.vo.work.WorkDetailInfoVo;
import com.zhuanquan.app.common.view.vo.work.WorkInspirationInfoVo;
import com.zhuanquan.app.common.view.vo.work.WorkMediaSourceCategoryView;
import com.zhuanquan.app.dal.dao.work.WorkAttenderDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseExtendDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceExtendDAO;
import com.zhuanquan.app.dal.dao.work.WorkHotIndexDAO;
import com.zhuanquan.app.dal.dao.work.WorkInspirationDAO;
import com.zhuanquan.app.dal.dao.work.WorkMilestoneDAO;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.cache.WorkRoleDefineCache;
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

	@Resource
	private TagCache tagCache;

	@Resource
	private WorkRoleDefineCache workRoleDefineCache;

	@Resource
	private WorkInspirationDAO workInspirationDAO;

	@Resource
	private WorkMilestoneDAO workMilestoneDAO;

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

		return lazyInitWorkTags(workId);
	}

	/**
	 * 
	 * @param workId
	 * @return
	 */
	private List<WorkTagMapping> lazyInitWorkTags(long workId) {

		String key = RedisKeyBuilder.getWorkTagsCacheKey(workId);

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

	/**
	 * 初始化作品的扩展属性cache，key是属性类型
	 * 
	 * @param workId
	 * @return
	 */
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

		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(key);

		if (hasKey) {
			// key为 属性id，value为 list结构的WorkAttender
			Map<String, String> resultMap = redisHelper.hashGetByKey(key);

			if (resultMap == null) {
				return null;
			}

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

		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(key);

		if (hasKey) {
			// key为 属性id，value为 list结构的WorkContentSource
			Map<String, String> resultMap = redisHelper.hashGetByKey(key);

			if (resultMap == null) {

				return null;
			}

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
		Set<String> sets = redisHelper.zsetRevrange(hotWorkKey, request.getFromIndex(),
				request.getFromIndex() + request.getLimit() - 1);

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
				if (attender.getRoleCode().equals(WorkRoleTypeConstants.WORK_ROLE_PRODUCTOR)) {
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

	@Override
	public WorkDetailInfoVo queryWorkDetail(long workId) {

		String key = RedisKeyBuilder.getWorkDetailInfoKey(workId);

		String jsonStr = redisHelper.valueGet(key);

		if (!StringUtils.isEmpty(jsonStr)) {

			return JSON.parseObject(jsonStr, WorkDetailInfoVo.class);
		}

		return lazyInitWorkDetailInfo(workId);
	}

	/**
	 * 懒记载作品详情信息
	 * 
	 * @param workId
	 * @return
	 */
	private WorkDetailInfoVo lazyInitWorkDetailInfo(long workId) {

		WorkDetailInfoVo vo = new WorkDetailInfoVo();

		// 基础信息
		WorkBase base = queryWorkById(workId);
		vo.setSummary(base.getSummary());
		vo.setWorkName(base.getSubject());
		vo.setWorkId(workId);
		vo.setCovPicUrl(base.getCovPicUrl());

		// 设置tag信息
		setWorkTagInfoToWorkDetail(vo, workId);

		// 设置作品的参与人信息
		setWorkAttederInfoToWorkDetail(vo, workId);

		// 设置作品的多媒体资源信息
		setWorkContentSourceToWorkDetail(vo, workId);

		// 设置创作灵感信息
		setWorkInspirationToWorkDetail(vo, workId);

		// 添加总评论数等扩展信息
		parseWorkExtendAttrToWorkDetail(vo, workId);
		
		//设置作品创作里程碑
		parseWorkMileStoneToWorkDetail(vo, workId);

		String key = RedisKeyBuilder.getWorkDetailInfoKey(workId);

		redisHelper.valueSet(key, JSON.toJSONString(vo), 1, TimeUnit.HOURS);

		return vo;

	}

	/**
	 * 设置作品的tag信息到作品详情页面
	 * 
	 * @param vo
	 * @param workId
	 */
	private void setWorkTagInfoToWorkDetail(WorkDetailInfoVo vo, long workId) {

		List<WorkTagMapping> workTagsMappings = lazyInitWorkTags(workId);

		if (CollectionUtils.isNotEmpty(workTagsMappings)) {

			List<TagInfoBo> tagList = new ArrayList<TagInfoBo>();

			// 此处可以优化代码，批量获取，赶时间，先这样吧
			for (WorkTagMapping record : workTagsMappings) {

				TagInfoBo bo = new TagInfoBo();
				bo.setTagId(record.getTagId());

				Tag tag = tagCache.getTagById(record.getTagId());

				Assert.notNull(tag);
				bo.setTagName(tag.getTagName());
				bo.setTagType(tag.getTagType());

				tagList.add(bo);
			}

			if (!CollectionUtils.isEmpty(tagList)) {
				vo.setTagList(tagList);
			}
		}

	}

	private void setWorkAttederInfoToWorkDetail(WorkDetailInfoVo vo, long workId) {

		// 作品参与人信息
		Map<String, List<WorkAttender>> attenderMap = lazyInitWorkAttenderCache(workId);
		if (MapUtils.isEmpty(attenderMap)) {
			return;
		}

		// 以角色为视图的map
		Map<String, Map<String, AuthorBriefInfoBo>> roleViewMap = new HashMap<String, Map<String, AuthorBriefInfoBo>>();

		for (Entry<String, List<WorkAttender>> entry : attenderMap.entrySet()) {

			List<WorkAttender> temp = entry.getValue();

			if (CollectionUtils.isNotEmpty(temp)) {
				for (WorkAttender atetnder : temp) {

					Map<String, AuthorBriefInfoBo> map = roleViewMap.get(atetnder.getRoleCode());

					if (map == null) {

						// 作者id
						long authorId = atetnder.getAuthorId();

						map = new HashMap<String, AuthorBriefInfoBo>();

						AuthorBaseInfoBo base = authorCache.queryAuthorBaseById(authorId);
						Asserts.notNull(base, "AuthorBaseInfoBo");

						map.put(authorId + "", AuthorBriefInfoBo.getObjectFromAuthorBase(base));

						roleViewMap.put(atetnder.getRoleCode(), map);

					} else {
						long authorId = atetnder.getAuthorId();
						// map中不含有这个作者的信息
						if (!map.containsKey(authorId + "")) {

							AuthorBaseInfoBo base = authorCache.queryAuthorBaseById(authorId);
							Asserts.notNull(base, "AuthorBaseInfoBo");

							map.put(atetnder.getAuthorId() + "", AuthorBriefInfoBo.getObjectFromAuthorBase(base));
						}

					}

				}
			}
		}

		if (MapUtils.isEmpty(roleViewMap)) {
			return;
		}

		List<WorkAttenderRoleViewVo> authorList = new ArrayList<WorkAttenderRoleViewVo>();

		for (Entry<String, Map<String, AuthorBriefInfoBo>> entry : roleViewMap.entrySet()) {

			WorkAttenderRoleViewVo roleView = new WorkAttenderRoleViewVo();

			// 角色code
			roleView.setRoleCode(entry.getKey());

			WorkRoleDefine roleDefine = workRoleDefineCache.queryRoleDefine(entry.getKey());

			Assert.notNull(roleDefine);

			roleView.setRoleName(roleDefine.getRoleDesc());

			Map<String, AuthorBriefInfoBo> map = entry.getValue();

			if (MapUtils.isNotEmpty(map)) {
				List<AuthorBriefInfoBo> list = new ArrayList<AuthorBriefInfoBo>(map.values());
				roleView.setAttenders(list);
				authorList.add(roleView);
			}

		}

		vo.setAuthorList(authorList);

	}

	/**
	 * 设置多媒体资源到 作品详情页
	 * 
	 * @param vo
	 * @param workId
	 */
	private void setWorkContentSourceToWorkDetail(WorkDetailInfoVo vo, long workId) {

		List<WorkMediaSourceCategoryView> mediaSources = new ArrayList<WorkMediaSourceCategoryView>();

		Map<String, List<WorkContentSource>> sourceMap = lazyInitWorkContentSourceCache(workId);

		if (MapUtils.isEmpty(sourceMap)) {
			return;
		}

		// key为资源category，
		for (Entry<String, List<WorkContentSource>> entry : sourceMap.entrySet()) {

			WorkMediaSourceCategoryView view = new WorkMediaSourceCategoryView();

			view.setSourceCategory(Integer.parseInt(entry.getKey()));

			List<WorkContentSource> temp = entry.getValue();

			if (CollectionUtils.isNotEmpty(temp)) {

				List<WorkContentSourceBriefInfoBo> sourceList = new ArrayList<WorkContentSourceBriefInfoBo>();
				for (WorkContentSource tempSource : temp) {

					WorkContentSourceBriefInfoBo bo = new WorkContentSourceBriefInfoBo();

					bo.setPlatformId(tempSource.getPlatformId());
					bo.setSourceCategory(tempSource.getSourceCategory());
					bo.setSourceId(tempSource.getSourceId());
					bo.setSourceType(tempSource.getSourceType());
					bo.setSourceVal(tempSource.getSourceVal());

					sourceList.add(bo);
				}

				view.setSourceList(sourceList);
				mediaSources.add(view);
			}
		}

		if (CollectionUtils.isNotEmpty(mediaSources)) {

			vo.setMediaSources(mediaSources);
		}

	}

	/**
	 * 添加创作灵感
	 * 
	 * @param vo
	 * @param workId
	 */
	private void setWorkInspirationToWorkDetail(WorkDetailInfoVo vo, long workId) {

		// 获取创作灵感，这个不能用缓存，因为可能更新很快
		List<WorkInspiration> list = workInspirationDAO.queryByWorkId(workId);

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		//

		List<WorkInspirationInfoVo> target = new ArrayList<WorkInspirationInfoVo>();
		for (WorkInspiration reocrd : list) {

			WorkInspirationInfoVo instance = new WorkInspirationInfoVo();

			instance.setAuthorId(reocrd.getAuthorId());
			AuthorBaseInfoBo bo = authorCache.queryAuthorBaseById(reocrd.getAuthorId());
			Assert.notNull(bo);

			instance.setAuthorName(bo.getAuthorName());
			instance.setHeadUrl(bo.getHeadUrl());

			instance.setCreateTime(reocrd.getModifyTime());
			instance.setInspiration(reocrd.getInspiration());

			target.add(instance);
		}

		vo.setWorkInspirationList(target);
	}

	/**
	 * 添加评论总数
	 * 
	 * @param vo
	 * @param workId
	 */
	private void parseWorkExtendAttrToWorkDetail(WorkDetailInfoVo vo, long workId) {
		Map<String, List<WorkBaseExtend>> map = lazyInitWorkBaseExtendCache(workId);

		// 解析总评论数
		List<WorkBaseExtend> commentList = map.get(WorkExtendAttrConstants.EXTEND_ATTR_COMMENT_TOTAL_NUM + "");
		if (CollectionUtils.isNotEmpty(commentList)) {
			Assert.isTrue(commentList.size() == 1);
			long totalComment = Long.parseLong(commentList.get(0).getAttrVal());
			vo.setTotalComment(totalComment);
		}

	}

	/**
	 * 解析作品里程碑
	 * 
	 * @param vo
	 * @param workId
	 */
	private void parseWorkMileStoneToWorkDetail(WorkDetailInfoVo vo, long workId) {

		List<WorkMilestone> list = workMilestoneDAO.queryMileStoneByWorkId(workId);

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<WorkMilestoneBo> target = new ArrayList<WorkMilestoneBo>();

		for (WorkMilestone record : list) {

			WorkMilestoneBo bo = WorkMilestoneBo.getBoFromWorkMilestone(record);
			target.add(bo);

		}

		vo.setMilestoneList(target);

	}

}