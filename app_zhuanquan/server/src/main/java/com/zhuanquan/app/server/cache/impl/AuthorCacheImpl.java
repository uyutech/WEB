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
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.constants.author.AuthorExtendInfoConstants;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.common.model.author.AuthorExtendInfo;
import com.zhuanquan.app.common.model.author.AuthorHotIndexes;
import com.zhuanquan.app.common.model.author.AuthorTagMapping;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.work.WorkAlbum;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.common.utils.CommonUtil;
import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorPartnerInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorPlatformInfoBo;
import com.zhuanquan.app.common.view.bo.work.AuthorAlbumInfoBo;
import com.zhuanquan.app.common.view.bo.work.AuthorWorkInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkBriefInfoBo;
import com.zhuanquan.app.common.view.vo.author.AuthorAlbumPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorHomeInfoVo;
import com.zhuanquan.app.common.view.vo.author.AuthorRelationshipPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorWorksPageQueryVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.dal.dao.author.AuthorExtendInfoDAO;
import com.zhuanquan.app.dal.dao.author.AuthorHotIndexesDAO;
import com.zhuanquan.app.dal.dao.author.AuthorTagMappingDAO;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformDefineDAO;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformInfoDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.work.WorkAlbumDAO;
import com.zhuanquan.app.dal.dao.work.WorkAlbumMemberDAO;
import com.zhuanquan.app.dal.dao.work.WorkAttenderDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;
import com.zhuanquan.app.server.cache.AuthorThirdPlatformCache;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.cache.WorksCache;

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

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;

	@Resource
	private AuthorThirdPlatformCache authorThirdPlatformCache;

	@Resource
	private WorkAttenderDAO workAttenderDAO;

	@Resource
	private WorksCache worksCache;

	@Resource
	private WorkBaseDAO workBaseDAO;

	@Resource
	private WorkAlbumDAO workAlbumDAO;

	@Resource
	private WorkAlbumMemberDAO workAlbumMemberDAO;

	@Override
	public AuthorBaseInfoBo queryAuthorBaseById(long authorId) {
		Map<String, AuthorBaseInfoBo> map = batchQueryAuthorBaseByIds(Lists.newArrayList(authorId));

		if (MapUtils.isEmpty(map)) {
			return null;
		}

		return map.get(authorId + "");
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
		// //
		//
		// 把缓存中缺少的通过这个方式补上去
		if (CollectionUtils.isNotEmpty(exclude)) {

			List<AuthorBase> list = authorBaseDAO.queryByAuthorIds(exclude);

			Map<String, String> needCommitMap = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(list)) {

				// Map<String,Pair<String, String>> map =
				// generateTagStrMap(exclude);

				for (AuthorBase base : list) {

					//

					AuthorBaseInfoBo bo = new AuthorBaseInfoBo();
					bo.setAuthorId(base.getAuthorId());
					bo.setAuthorName(base.getAuthorName());
					bo.setHeadUrl(base.getHeadUrl());

					// Pair<String, String> pair =
					// map.get(base.getAuthorId().toString());
					// if(pair!=null) {
					// bo.setTagDesc(pair.getLeft());
					// bo.setTagIds(pair.getRight());
					// }

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
	private Map<String, Pair<String, String>> generateTagStrMap(List<Long> authorIds) {

		Map<String, Pair<String, String>> map = new HashMap<String, Pair<String, String>>();

		// order by authorid，ordernum,create_time
		List<AuthorTagMapping> tagMapList = authorTagMappingDAO.queryByAuthorIds(authorIds);

		if (CollectionUtils.isEmpty(tagMapList)) {
			return map;
		}

		for (AuthorTagMapping record : tagMapList) {

			long tagId = record.getTagId();

			Tag tag = tagCache.getTagById(tagId);
			if (tag == null) {
				continue;
			}

			Pair<String, String> pair = map.get(record.getAuthorId().toString());

			if (pair == null) {

				map.put(record.getAuthorId().toString(), Pair.of(tag.getTagName(), tag.getTagId().toString()));

			} else {

				String tagDesc = pair.getLeft() + "," + tag.getTagName();

				String tagIdStr = pair.getRight() + "," + tag.getTagId().toString();

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

		String key = RedisKeyBuilder.getAuthorHotIndexKey(authorId);

		// 获取key的值
		String value = redisHelper.valueGet(key);

		if (value != null) {
			return Long.parseLong(value);
		}

		AuthorHotIndexes record = authorHotIndexesDAO.queryByAuthorId(authorId);

		if (record == null) {
			return 0L;
		}

		redisHelper.valueSet(key, record.getScore().toString(), 3, TimeUnit.MINUTES);

		return record.getScore();
	}

	@Override
	public AuthorThirdPlatformDefine queryById(long platformId) {

		String key = RedisKeyBuilder.getPlatformDefineKey();

		String val = redisHelper.hashGet(key, platformId + "");

		if (StringUtils.isNotEmpty(val)) {

			return JSON.parseObject(val, AuthorThirdPlatformDefine.class);
		}

		List<AuthorThirdPlatformDefine> all = authorThirdPlatformDefineDAO.queryAllInfo();

		if (CollectionUtils.isEmpty(all)) {
			return null;
		}

		AuthorThirdPlatformDefine target = null;

		Map<String, String> map = new HashMap<String, String>();

		for (AuthorThirdPlatformDefine record : all) {
			if (record.getId().longValue() == platformId) {
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

		if (val != null) {
			return JSON.parseArray(val, AuthorThirdPlatformInfo.class);
		}

		List<AuthorThirdPlatformInfo> list = authorThirdPlatformInfoDAO.queryByAuthorId(authorId);

		if (CollectionUtils.isEmpty(list)) {
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

		boolean hasKey = redisHelper.getGracefulRedisTemplate().hasKey(hotKey);

		if (hasKey) {
			// 尝试从zset缓存中获取
			Set<String> sets = redisHelper.zsetRevrange(hotKey, request.getFromIndex(),
					request.getFromIndex() + request.getLimit() - 1);

			// 缓存中有值
			if (sets != null && sets.size() != 0) {

				return CommonUtil.deserializArray(sets, DiscoveryHotAuthorVo.class);
			} else {
				return null;
			}

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

		Set<String> sets = redisHelper.zsetRevrange(hotKey, request.getFromIndex(),
				request.getFromIndex() + request.getLimit() - 1);

		return sets != null ? (CommonUtil.deserializArray(sets, DiscoveryHotAuthorVo.class)) : null;

		// return resultList;
	}

	@Override
	public AuthorHomeInfoVo queryAuthorHomeInfoVo(long authorId) {

		String key = RedisKeyBuilder.getAuthorHomeInfoKey(authorId);

		String jsonVal = redisHelper.valueGet(key);

		if (StringUtils.isNotEmpty(jsonVal)) {
			AuthorHomeInfoVo vo = JSON.parseObject(jsonVal, AuthorHomeInfoVo.class);
			vo.setHasFollowed(checkIsFollowedAuthor(authorId));
			return vo;
		}

		AuthorHomeInfoVo vo = lazyInitAuthorHomeInfoCache(authorId);
		vo.setHasFollowed(checkIsFollowedAuthor(authorId));

		return vo;
	}

	/**
	 * 懒加载缓存
	 * 
	 * @param authorId
	 * @return
	 */
	private AuthorHomeInfoVo lazyInitAuthorHomeInfoCache(long authorId) {

		AuthorHomeInfoVo vo = new AuthorHomeInfoVo();

		// 设置基本信息
		AuthorBaseInfoBo base = queryAuthorBaseById(authorId);
		Assert.notNull(base);
		vo.setAuthorId(authorId);
		vo.setAuthorName(base.getAuthorName());
		vo.setHeadUrl(base.getHeadUrl());

		// 设置作者扩展属性，比如 评论数，昵称等
		setAuthorExtendInfoToHomeInfo(vo, authorId);

		// 设置第三方平台信息
		setThirdPlatInfoToHomeInfo(vo, authorId);

		// 设置作者热度信息
		setAuthorHotScoreToHomeInfo(vo, authorId);

		// 设置作品和专辑信息
		setAuthorWorksInfoToHomeInfo(vo, authorId);

		// 设置作者的 职种角色信息，1级分类的
		setAuthorRoleTypesToHomeInfo(vo, authorId);

		// 设置.关系视图
		AuthorRelationshipPageQueryVo relationView = pageQueryAuthorRelationship(authorId, 0, 5);
		vo.setRelationView(relationView);

		String key = RedisKeyBuilder.getAuthorHomeInfoKey(authorId);

		redisHelper.valueSet(key, JSON.toJSONString(vo), 5, TimeUnit.MINUTES);

		return vo;
	}

	/**
	 * 
	 * @param authorId
	 * @return
	 */
	private boolean checkIsFollowedAuthor(long authorId) {

		long uid = SessionHolder.getCurrentLoginUid();

		if (uid <= 0) {
			return false;
		}

		return userFollowAuthorDAO.queryHasFollowAuthor(uid, authorId);
	}

	/**
	 * 扩展信息map，key是扩展属性类型
	 * 
	 * @param authorId
	 * @return
	 */
	private Map<String, AuthorExtendInfo> getAuthorExtendInfoMap(long authorId) {

		List<AuthorExtendInfo> extendList = authorExtendInfoDAO.queryByAuthorId(authorId);

		if (CollectionUtils.isEmpty(extendList)) {
			return null;
		}

		Map<String, AuthorExtendInfo> map = new HashMap<String, AuthorExtendInfo>();
		for (AuthorExtendInfo ex : extendList) {
			map.put(ex.getAttrType().toString(), ex);
		}

		return map;
	}

	/**
	 * 把作者的扩展信息设置到 homeinfo
	 * 
	 * @param vo
	 * @param authorId
	 */
	private void setAuthorExtendInfoToHomeInfo(AuthorHomeInfoVo vo, long authorId) {

		Map<String, AuthorExtendInfo> attrMap = getAuthorExtendInfoMap(authorId);

		if (MapUtils.isEmpty(attrMap)) {
			return;
		}

		// 总评论数
		AuthorExtendInfo fansNumObj = attrMap.get(String.valueOf(AuthorExtendInfoConstants.EX_ATTR_FANS_NUM));

		if (fansNumObj != null) {
			vo.setFansNum(Long.parseLong(fansNumObj.getValue()));
		}

		// 昵称
		AuthorExtendInfo nickNameObj = attrMap.get(String.valueOf(AuthorExtendInfoConstants.EX_ATTR_NICK_NAME));
		if (nickNameObj != null) {
			vo.setNickName(fansNumObj.getValue());
		}

	}

	/**
	 * 设置第三方平台信息
	 * 
	 * @param vo
	 * @param authorId
	 */
	private void setThirdPlatInfoToHomeInfo(AuthorHomeInfoVo vo, long authorId) {

		List<AuthorThirdPlatformInfo> thirdPlatList = queryAuthorOtherPlatformInfo(authorId);

		if (CollectionUtils.isEmpty(thirdPlatList)) {
			return;
		}

		List<AuthorPlatformInfoBo> targetList = new ArrayList<AuthorPlatformInfoBo>();

		for (AuthorThirdPlatformInfo record : thirdPlatList) {

			AuthorPlatformInfoBo bo = new AuthorPlatformInfoBo();

			bo.setHomePage(record.getHomePage());

			bo.setPlatformId(record.getPlatformId());

			AuthorThirdPlatformDefine define = authorThirdPlatformCache.queryThirdPlatformInfo(record.getPlatformId());

			Assert.notNull(define);

			bo.setPlatformName(define.getName());
			bo.setType(define.getType());
			bo.setPlatformLogo(define.getLogoUrl());

			targetList.add(bo);
		}

		vo.setThirdPlatList(targetList);

	}

	/**
	 * 设置作者的热度信息
	 * 
	 * @param vo
	 * @param authorId
	 */
	private void setAuthorHotScoreToHomeInfo(AuthorHomeInfoVo vo, long authorId) {

		AuthorHotIndexes index = authorHotIndexesDAO.queryByAuthorId(authorId);

		if (index == null) {
			return;
		}

		vo.setHotScore(index.getScore());
	}

	/**
	 * 设置作者的作品信息
	 * 
	 * @param vo
	 * @param authorId
	 */
	private void setAuthorWorksInfoToHomeInfo(AuthorHomeInfoVo vo, long authorId) {

		List<Long> workIds = worksCache.queryAuthorAttendWorks(authorId);

		if (CollectionUtils.isEmpty(workIds)) {
			return;
		}

		// 作者相关作品
		AuthorWorksPageQueryVo workView = pageQueryAuthorWorksPageQueryVo(authorId, 0, 5);
		vo.setWorksView(workView);

		// 作者相关专辑
		AuthorAlbumPageQueryVo albumView = pageQueryAuthorAlbumsVo(authorId, 0, 5);
		vo.setAlbumView(albumView);

	}

	/**
	 * 根据作者参与的作品id，分页查询
	 * 
	 * @param workIds
	 * @param fromIndex
	 * @param limit
	 */
	@Override
	public AuthorWorksPageQueryVo pageQueryAuthorWorksPageQueryVo(long authorId, int fromIndex, int limit) {

		AuthorWorksPageQueryVo vo = new AuthorWorksPageQueryVo();
		vo.setAuthorId(authorId);
		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);

		List<Long> workIds = worksCache.queryAuthorAttendWorks(authorId);

		if (CollectionUtils.isEmpty(workIds)) {
			return vo;
		}

		List<WorkBase> list = workBaseDAO.queryWorksInfoByPage(workIds, fromIndex, limit);

		if (CollectionUtils.isEmpty(list)) {
			return vo;
		}

		List<WorkBriefInfoBo> target = new ArrayList<>();

		for (WorkBase base : list) {

			WorkBriefInfoBo bo = new WorkBriefInfoBo();
			bo.setCovPic(base.getCovPicUrl());
			bo.setWorkId(base.getWorkId());
			bo.setWorkName(base.getSubject());
			target.add(bo);
		}

		vo.setWorksList(target);

		return vo;
	}

	/**
	 * 获取作品相关专辑视图
	 * 
	 * @param workIds
	 * @param authorId
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	@Override
	public AuthorAlbumPageQueryVo pageQueryAuthorAlbumsVo(long authorId, int fromIndex, int limit) {

		AuthorAlbumPageQueryVo vo = new AuthorAlbumPageQueryVo();
		vo.setAuthorId(authorId);
		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);

		List<Long> workIds = worksCache.queryAuthorAttendWorks(authorId);

		if (CollectionUtils.isEmpty(workIds)) {
			return vo;
		}

		List<Long> albumIds = workAlbumMemberDAO.queryAlbumIdsByWorkIds(workIds, fromIndex, limit);

		if (CollectionUtils.isEmpty(albumIds)) {
			return vo;
		}

		// 按albumid 从大到小排序了
		List<WorkAlbum> workAlbums = workAlbumDAO.queryByAlbumIds(albumIds);

		Assert.notEmpty(workAlbums);

		List<AuthorAlbumInfoBo> target = new ArrayList<>();
		for (WorkAlbum record : workAlbums) {
			AuthorAlbumInfoBo bo = new AuthorAlbumInfoBo();

			bo.setAlbumId(record.getAlbumId());
			bo.setAlbumName(record.getSubject());

			target.add(bo);
		}

		vo.setAlbumList(target);

		return vo;
	}

	/**
	 * 设置作者的职种角色类型(1级分类)到home
	 * 
	 * @param vo
	 * @param authorId
	 */
	private void setAuthorRoleTypesToHomeInfo(AuthorHomeInfoVo vo, long authorId) {

		List<String> roleCodes = workAttenderDAO.queryAuthorRoleCodesByAuthorId(authorId);

		if (CollectionUtils.isEmpty(roleCodes)) {
			return;
		}

		Map<String, String> roleTypeMap = new HashMap<>();

		// 解析前三位，并且去重
		for (String roleCode : roleCodes) {

			Assert.isTrue(StringUtils.isNotEmpty(roleCode) && roleCode.length() >= 3);
			// 取前三位,就是类型的定义
			String prefix = roleCode.substring(0, 3);
			roleTypeMap.put(prefix, prefix);
		}

		List<String> roleTypes = new ArrayList<String>();
		roleTypes.addAll(roleTypeMap.values());

		if (CollectionUtils.isNotEmpty(roleTypes)) {
			vo.setRoleTypes(roleTypes);
		}

	}

	/**
	 * 合作关系
	 * 
	 * @param vo
	 * @param authorId
	 */
	@Override
	public AuthorRelationshipPageQueryVo pageQueryAuthorRelationship(long authorId, int fromIndex, int limit) {

		AuthorRelationshipPageQueryVo vo = new AuthorRelationshipPageQueryVo();
		vo.setAuthorId(authorId);
		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);

		List<Long> workIds = worksCache.queryAuthorAttendWorks(authorId);

		if (CollectionUtils.isEmpty(workIds)) {
			return vo;
		}

		List<AuthorPartnerInfoBo> list = worksCache.pageQueryAuthorPartnerInfo(authorId, fromIndex, limit);

		if (CollectionUtils.isEmpty(list)) {
			return vo;
		}

		vo.setPartnerList(list);

		return vo;
	}

}