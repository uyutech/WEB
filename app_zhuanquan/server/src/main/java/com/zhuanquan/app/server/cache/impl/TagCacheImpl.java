package com.zhuanquan.app.server.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.RedisZSetOperations;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorUnit;
import com.zhuanquan.app.dal.dao.author.TagDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.server.cache.TagCache;

/**
 * 
 * @author zhangjun
 *
 */
@Repository
public class TagCacheImpl extends CacheChangedListener implements TagCache {

	@Resource
	private TagDAO tagDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TagInfoBo> getSuggestTag(long uid, int fromIndex, int limit) {

		String privateSuggestKey = RedisKeyBuilder.getPrivateHotTagsSuggestKey(uid);

		boolean hasKey = redisHelper.hasKey(privateSuggestKey);
		
		if(hasKey) {
		// 尝试从zset缓存中获取
		Set<String> sets = redisHelper.zsetRevrange(privateSuggestKey, fromIndex, fromIndex + limit - 1);

		// 缓存中有值
		if (sets != null && sets.size() != 0) {

			return CommonUtil.deserializArray(sets, TagInfoBo.class);
		} else {
			return null;
		}

		}
		// 缓存中没有，尝试初始化

		String publicHotkey = RedisKeyBuilder.getPublicHotTagsSuggestKey();

		String hotTop100ListStr = redisHelper.valueGet(publicHotkey);

		List<TagInfoBo> list = new ArrayList<TagInfoBo>();

		if (hotTop100ListStr == null) {

			list = lazyInitGlobalTop100HotTag();

		} else {

			list = JSON.parseArray(hotTop100ListStr, TagInfoBo.class);
		}

		// 设置个人的缓存，有效期为5分钟

		Set<TypedTuple<String>> set = new LinkedHashSet<TypedTuple<String>>(list.size());

		//zset按照score排序，即index
		for (int index = 0; index < list.size(); index++) {
			set.add(new DefaultTypedTuple(JSON.toJSONString(list.get(index)), (double) index));
		}

		redisHelper.zsetAdd(privateSuggestKey, set);
		redisHelper.expire(privateSuggestKey, 5, TimeUnit.MINUTES);

		Set<String> sets = redisHelper.zsetRevrange(privateSuggestKey, fromIndex, fromIndex + limit - 1);

		
		return sets != null ? (CommonUtil.deserializArray(sets, TagInfoBo.class)) : null;
//
//		
//		return CommonUtil.deserializArray(sets, TagInfoBo.class);

	}

	/**
	 * 懒加载 global的 top 100
	 * 
	 * @return
	 */
	public List<TagInfoBo> lazyInitGlobalTop100HotTag() {

		List<TagInfoBo> list = new ArrayList<TagInfoBo>();

		String publicHotkey = RedisKeyBuilder.getPublicHotTagsSuggestKey();

		// 查询最近2天 top100的tag
		List<Long> tagIds = userFollowTagsMappingDAO.queryHotTagsRecently(100);

		if (CollectionUtils.isEmpty(tagIds)) {
			return null;
		}

		Map<String, Tag> map = this.getTagMapByIds(tagIds);

		for (Entry<String, Tag> entry : map.entrySet()) {
			Tag tag = entry.getValue();

			if (tag != null) {
				TagInfoBo record = new TagInfoBo();

				record.setTagId(tag.getTagId());
				record.setTagName(tag.getTagName());
				record.setTagType(tag.getTagType());

				list.add(record);
			}
		}

		// 设置global缓存，1小时有效期
		redisHelper.valueSet(publicHotkey, JSON.toJSONString(list), 60, TimeUnit.MINUTES);

		return list;
	}

	/**
	 * 获取随机的一组key
	 * 
	 * @param needKeyNum
	 * @param totalTagNum
	 * @return
	 */
	private List<String> getRandomKeys(int needKeyNum, int totalTagNum) {

		return null;

	}

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {

		return Lists.newArrayList(RedisCacheEnum.REDIS_CACHE_TAG);
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {

	}

	@Override
	public Map<String, Tag> getTagMapByIds(List<Long> tagIds) {

		if (CollectionUtils.isEmpty(tagIds)) {
			return null;
		}

		String key = RedisKeyBuilder.getTagsIdsKey();

		List<String> tagIdsHashKey = new ArrayList<String>();

		for (Long id : tagIds) {
			tagIdsHashKey.add(id.toString());
		}

		List<String> tags = redisHelper.hashMultiGet(key, tagIdsHashKey);

		List<Long> exclude = new ArrayList<Long>();

		Map<String, Tag> resultMap = new HashMap<String, Tag>();

		for (int index = 0; index < tagIdsHashKey.size(); index++) {

			String tagStr = tags.get(index);

			// 表示redis没有，需要懒加载进去
			if (StringUtils.isEmpty(tagStr)) {
				exclude.add(tagIds.get(index));

				// 先设置为null
				resultMap.put(tagIdsHashKey.get(index), null);
			} else {
				resultMap.put(tagIdsHashKey.get(index), JSON.parseObject(tagStr, Tag.class));
			}

		}

		//

		if (CollectionUtils.isNotEmpty(exclude)) {
			List<Tag> list = tagDAO.queryTagsByIds(exclude);

			Map<String, String> needCommitMap = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(list)) {

				for (Tag tag : list) {
					resultMap.put(tag.getTagId().toString(), tag);
					needCommitMap.put(tag.getTagId().toString(), JSON.toJSONString(tag));
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

	@Override
	public List<Tag> getTagListByIds(List<Long> tagIds) {

		if (CollectionUtils.isEmpty(tagIds)) {
			return null;
		}

		Map<String, Tag> map = getTagMapByIds(tagIds);

		if (MapUtils.isEmpty(map)) {
			return null;
		}

		List<Tag> list = new ArrayList<Tag>();

		for (Entry<String, Tag> entry : map.entrySet()) {
			if (entry.getValue() != null)
				list.add(entry.getValue());
		}

		return list;
	}

	@Override
	public Tag getTagById(long tagId) {
		
		List<Tag> list = this.getTagListByIds(Lists.newArrayList(tagId));
		
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	
	
	

}