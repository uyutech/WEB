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
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.user.QueryUserFollowAuthorsResponseVo;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
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

}