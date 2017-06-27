package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.common.model.author.AuthorHotIndexes;
import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.author.AuthorAlbumPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorHomeInfoVo;
import com.zhuanquan.app.common.view.vo.author.AuthorRelationshipPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorWorksPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorUnit;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;
import com.zhuanquan.app.server.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private VipAuthorOpenAccountMappingDAO vipAuthorOpenAccountMappingDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private AuthorCache authorCache;

	@Resource
	private AuthorHotIndexesCache authorHotIndexesCache;

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;

	@Override
	public SuggestAuthorResponseVo getSuggestAuthors(SuggestAuthorRequestVo vo) {

		List<SuggestAuthorUnit> list = getSuggestAuthorList(vo);

		SuggestAuthorResponseVo response = new SuggestAuthorResponseVo();

		response.setDataList(list);
		response.setFromIndex(vo.getFromIndex());
		response.setLimit(vo.getLimit());
		response.setUid(vo.getUid());

		return response;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<SuggestAuthorUnit> getSuggestAuthorList(SuggestAuthorRequestVo vo) {

		String hotKey = RedisKeyBuilder.getSuggestHotAuthorKey(vo.getUid());

		boolean hasKey = redisHelper.hasKey(hotKey);

		if (hasKey) {
			// 尝试从zset缓存中获取
			Set<String> sets = redisHelper.zsetRevrange(hotKey, vo.getFromIndex(),
					vo.getFromIndex() + vo.getLimit() - 1);

			if (sets != null && sets.size() != 0) {
				return CommonUtil.deserializArray(sets, SuggestAuthorUnit.class);
			} else {
				return null;
			}

		}

		List<SuggestAuthorUnit> allList = lazyFetchSuggestAuthorInfo(vo);

		if (CollectionUtils.isEmpty(allList)) {
			return null;
		}

		// 设置个人的缓存，有效期为5分钟

		Set<TypedTuple<String>> set = new LinkedHashSet<TypedTuple<String>>(allList.size());

		// zset按照score排序，即index
		for (int index = 0; index < allList.size(); index++) {
			set.add(new DefaultTypedTuple(JSON.toJSONString(allList.get(index)), (double) index));
		}

		redisHelper.zsetAdd(hotKey, set);
		redisHelper.expire(hotKey, 5, TimeUnit.MINUTES);

		Set<String> sets = redisHelper.zsetRevrange(hotKey, vo.getFromIndex(), vo.getFromIndex() + vo.getLimit() - 1);

		return sets != null ? (CommonUtil.deserializArray(sets, SuggestAuthorUnit.class)) : null;
	}

	/**
	 * 懒加载
	 * 
	 * @param vo
	 * @return
	 */
	private List<SuggestAuthorUnit> lazyFetchSuggestAuthorInfo(SuggestAuthorRequestVo vo) {

		// key ，这个key是之前注册时，同步第三方关注的作者列表信息
		String key = RedisKeyBuilder.getOpenAccountSyncFollowAuthorKey(vo.getChannelType(), vo.getUid());

		String vipInfo = redisHelper.valueGet(key);

		List<VipAuthorOpenAccountMapping> list = new ArrayList<VipAuthorOpenAccountMapping>();

		if (vipInfo != null) {
			list = JSON.parseArray(vipInfo, VipAuthorOpenAccountMapping.class);
		}

		// 系统中热点的100个作者
		List<AuthorHotIndexes> globalHotList = authorHotIndexesCache.getAuthorHotIndexTop100();

		Map<Long, Boolean> map = mergeAndDetermin(list, globalHotList);

		Map<String, AuthorBaseInfoBo> authorMap = authorCache
				.batchQueryAuthorBaseByIds(new ArrayList<Long>(map.keySet()));

		if (MapUtils.isEmpty(authorMap)) {
			return null;
		}

		List<SuggestAuthorUnit> suggestList = new ArrayList<SuggestAuthorUnit>();

		for (Entry<String, AuthorBaseInfoBo> entry : authorMap.entrySet()) {

			AuthorBaseInfoBo base = entry.getValue();

			if (base == null) {
				continue;
			}

			SuggestAuthorUnit unit = new SuggestAuthorUnit();
			unit.setAuthorId(base.getAuthorId());
			unit.setAuthorName(base.getAuthorName());
			unit.setHeadUrl(base.getHeadUrl());
			unit.setIsDefaultFollow(map.get(Long.parseLong(entry.getKey())) ? 1 : 0);

			suggestList.add(unit);
		}

		return suggestList;

	}

	/**
	 * 将作者在第三方已经关注的作者信息，和我们这边的热点作者信息，进行组合，排出推荐的顺序
	 * 
	 * @param vipList
	 * @param globalHotList
	 * @return
	 */
	private Map<Long, Boolean> mergeAndDetermin(List<VipAuthorOpenAccountMapping> vipList,
			List<AuthorHotIndexes> globalHotList) {

		Map<Long, Boolean> result = new HashMap<Long, Boolean>();

		if (CollectionUtils.isNotEmpty(globalHotList)) {

			for (AuthorHotIndexes record : globalHotList) {

				// false表示不需要默认设置为 推荐关注
				result.put(record.getAuthorId(), false);
			}

		}

		if (CollectionUtils.isNotEmpty(vipList)) {
			for (VipAuthorOpenAccountMapping record : vipList) {
				// true表示默认设置为 推荐关注,因为是第三方推荐的
				result.put(record.getAuthorId(), true);
			}
		}

		return result;
	}

	@Override
	public AuthorHomeInfoVo queryAuthorHomeInfoVo(long authorId) {
		return authorCache.queryAuthorHomeInfoVo(authorId);
	}

	@Override
	public AuthorWorksPageQueryVo pageQueryAuthorWorksPageQueryVo(long authorId, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorAlbumPageQueryVo pageQueryAuthorAlbumsVo(long authorId, int fromIndex, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorRelationshipPageQueryVo pageQueryAuthorRelationship(long authorId, int fromIndex, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}