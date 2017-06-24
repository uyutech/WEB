package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.AuthorDynamics;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorDynamicsBo;
import com.zhuanquan.app.common.view.vo.author.PageQueryAuthorDynamicsVo;
import com.zhuanquan.app.common.view.vo.user.PageQueryFollowedAuthorsResponseVo;
import com.zhuanquan.app.common.view.vo.user.PageQueryFollowedTagsResponseVo;
import com.zhuanquan.app.common.view.vo.user.QueryUserFollowAuthorsResponseVo;
import com.zhuanquan.app.dal.dao.author.AuthorDynamicsDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorThirdPlatformCache;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.service.TransactionService;
import com.zhuanquan.app.server.service.UserFollowService;

/**
 * user service
 * 
 * @author zhangjun
 *
 */

@Service
public class UserFollowServiceImpl implements UserFollowService {

	private static final int TIMES_LIMIT = 6;

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private TransactionService transactionService;

	@Resource
	private AuthorCache authorCache;

	@Resource
	private TagCache tagCache;

	@Resource
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;

	@Resource
	private AuthorDynamicsDAO authorDynamicsDAO;

	@Resource
	private AuthorThirdPlatformCache authorThirdPlatformCache;

	@Override
	public void setUserFollowAuthors(long uid, List<Long> authorIds) {

		transactionService.setUserFollowAuthors(uid, authorIds);

		// TODO 更新关联的作者粉丝
	}

	@Override
	public void followAuthor(long uid, long authorId) {

		checkIsLimited(uid, authorId);

		boolean isFollowed = userFollowAuthorDAO.queryHasFollowAuthor(uid, authorId);

		if (isFollowed) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "已关注此作者");
		}

		//
		this.setUserFollowAuthors(uid, Lists.newArrayList(authorId));
	}

	@Override
	public void cancelFollowAuthor(long uid, long authorId) {

		checkIsLimited(uid, authorId);

		boolean isFollowed = userFollowAuthorDAO.queryHasFollowAuthor(uid, authorId);

		if (!isFollowed) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "未关注此作者");
		}

		transactionService.cancelFollowAuthor(uid, authorId);
	}

	/**
	 * 
	 * @param uid
	 * @param authorId
	 */
	private void checkIsLimited(long uid, long authorId) {

		// 操作过于频繁的控制
		//
		String key = RedisKeyBuilder.followAuthorTooManyTimesLock(uid, authorId);

		String value = redisHelper.valueGet(key);

		if (value != null && Integer.parseInt(value) > TIMES_LIMIT) {
			throw new BizException(BizErrorCode.EX_NOT_ALLOW_FREQUENT_OPER.getCode());
		} else {
			redisHelper.increase(key, 1);
			redisHelper.expire(key, 2, TimeUnit.MINUTES);
		}

	}

	@Override
	public QueryUserFollowAuthorsResponseVo queryFollowAuthors(long uid) {

		QueryUserFollowAuthorsResponseVo vo = new QueryUserFollowAuthorsResponseVo();
		vo.setUid(uid);

		List<Long> authorIds = userFollowAuthorDAO.queryUserFollowAuthorIds(uid);

		if (CollectionUtils.isEmpty(authorIds)) {
			return vo;
		}

		Map<String, AuthorBaseInfoBo> map = authorCache.batchQueryAuthorBaseByIds(authorIds);
		if (MapUtils.isEmpty(map)) {
			return vo;
		}

		List<AuthorBaseInfoBo> infoList = new ArrayList<AuthorBaseInfoBo>();

		for (Entry<String, AuthorBaseInfoBo> entry : map.entrySet()) {
			if (entry == null || entry.getValue() == null) {
				continue;
			}
			infoList.add(entry.getValue());
		}

		vo.setFollowAuthors(infoList);

		return vo;
	}

	@Override
	public PageQueryFollowedTagsResponseVo queryUserFollowTags(long uid, int fromIndex, int limit) {

		PageQueryFollowedTagsResponseVo vo = new PageQueryFollowedTagsResponseVo();
		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);
		vo.setUid(uid);

		List<Long> list = userFollowTagsMappingDAO.queryUserFollowTagByPage(uid, fromIndex, limit);

		if (CollectionUtils.isEmpty(list)) {
			return vo;
		}

		List<TagInfoBo> tagList = new ArrayList<TagInfoBo>();

		Map<String, Tag> map = tagCache.getTagMapByIds(list);

		for (Long id : list) {

			Tag tag = map.get(id.toString());
			if (tag != null) {

				TagInfoBo bo = new TagInfoBo();
				bo.setTagId(tag.getTagId());
				bo.setTagName(tag.getTagName());
				bo.setTagType(tag.getTagType());

				tagList.add(bo);
			}
		}

		vo.setTagList(tagList);

		return vo;
	}

	@Override
	public PageQueryFollowedAuthorsResponseVo queryUserFollowAuthors(long uid, int fromIndex, int limit) {

		PageQueryFollowedAuthorsResponseVo vo = new PageQueryFollowedAuthorsResponseVo();

		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);
		vo.setUid(uid);

		List<Long> list = userFollowAuthorDAO.queryFollowAuthorsByPage(uid, limit, fromIndex);

		if (CollectionUtils.isEmpty(list)) {
			return vo;
		}

		List<AuthorBriefInfoBo> result = new ArrayList<>();
		Map<String, AuthorBaseInfoBo> map = authorCache.batchQueryAuthorBaseByIds(list);

		for (Long id : list) {
			AuthorBaseInfoBo base = map.get(id.toString());
			if (base != null) {

				AuthorBriefInfoBo bo = new AuthorBriefInfoBo();
				bo.setAuthorId(base.getAuthorId());
				bo.setAuthorName(base.getAuthorName());
				bo.setHeadSnapshot(base.getHeadUrl());
				result.add(bo);
			}
		}

		vo.setAuthorList(result);

		return vo;
	}

	@Override
	public PageQueryAuthorDynamicsVo pageQueryFollowedAuthorDynamics(long uid, int fromIndex, int limit) {

		PageQueryAuthorDynamicsVo vo = new PageQueryAuthorDynamicsVo();

		vo.setFromIndex(fromIndex);
		vo.setLimit(limit);
		vo.setUid(uid);

		// 查询所有的关注作者id
		List<Long> list = userFollowAuthorDAO.queryUserFollowAuthorIds(uid);

		if (CollectionUtils.isEmpty(list)) {
			return vo;
		}

		List<AuthorDynamics> dynamics = authorDynamicsDAO.queryAuthorDynamicsByPage(list, fromIndex, limit);
		if (CollectionUtils.isEmpty(dynamics)) {
			return vo;

		}

		List<AuthorDynamicsBo> dynamicsBoList = new ArrayList<AuthorDynamicsBo>();

		for (AuthorDynamics dynamic : dynamics) {

			AuthorDynamicsBo bo = new AuthorDynamicsBo();

			bo.setPublicTime(dynamic.getPublishTime());

			//设置logo和平台id
			setDynamicsPlatformInfoLogo(bo, dynamic);

			// 声称动态描述
			bo.setContent(AuthorDynamics.getContent(dynamic));

			//设置作者信息
			setDynamicsAuthorInfo(bo, dynamic);
			
			
			dynamicsBoList.add(bo);
		}

		vo.setDynamicsList(dynamicsBoList);

		return vo;
	}
	
	
	/**
	 * 设置第三方平台信息到  AuthorDynamicsBo 对象
	 * @param bo
	 * @param dynamic
	 */
	private void setDynamicsPlatformInfoLogo(AuthorDynamicsBo bo,AuthorDynamics dynamic) {
		
		bo.setPlatformId(dynamic.getPlatformId());

		// 获取logo
		Map<String, AuthorThirdPlatformDefine> map = authorThirdPlatformCache
				.batchQueryThirdPlatformInfo(Lists.newArrayList(dynamic.getPlatformId()));
		if (MapUtils.isNotEmpty(map)) {
			AuthorThirdPlatformDefine define = map.get(dynamic.getPlatformId().toString());
			if (define != null) {
				bo.setPlatformLogoUrl(define.getLogoUrl());
			}
		}
	}
	
	
	/**
	 * 设置作者信息到 AuthorDynamicsBo
	 * @param bo
	 * @param dynamic
	 */
	private void setDynamicsAuthorInfo(AuthorDynamicsBo bo,AuthorDynamics dynamic) {
		
		Map<String, AuthorBaseInfoBo> authormap = authorCache
				.batchQueryAuthorBaseByIds(Lists.newArrayList(dynamic.getAuthorId()));
		AuthorBaseInfoBo base = authormap.get(dynamic.getAuthorId().toString());
		if (base != null) {

			AuthorBriefInfoBo authorBrief = new AuthorBriefInfoBo();
			authorBrief.setAuthorId(base.getAuthorId());
			authorBrief.setAuthorName(base.getAuthorName());
			authorBrief.setHeadSnapshot(base.getHeadUrl());
			bo.setAuthorList(Lists.newArrayList(authorBrief));

		}
	}
	
	
	
	

}