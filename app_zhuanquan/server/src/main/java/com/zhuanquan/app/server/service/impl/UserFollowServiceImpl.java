package com.zhuanquan.app.server.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
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

	@Override
	public void setUserFollowAuthors(long uid, List<Long> authorIds) {

		// 第三步注册完了，设置状态为normal
		userFollowAuthorDAO.insertBatchFollowAuthorIds(uid, authorIds);
	}

	@Override
	public void followAuthor(long uid, long authorId) {

		checkIsLimited(uid, authorId);

		boolean isFollowed = userFollowAuthorDAO.queryHasFollowAuthor(uid, authorId);

		if (!isFollowed) {
			userFollowAuthorDAO.insertOrUpdateToFollowAuthor(uid, authorId);
		}
	}

	@Override
	public void cancelFollowAuthor(long uid, long authorId) {

		checkIsLimited(uid, authorId);

		boolean isFollowed = userFollowAuthorDAO.queryHasFollowAuthor(uid, authorId);

		if (isFollowed) {

			userFollowAuthorDAO.updateToCancelFollowAuthor(uid, authorId);
		}
	}

	/**
	 * 
	 * @param uid
	 * @param authorId
	 */
	private void checkIsLimited(long uid, long authorId) {

		// 操作过于频繁的控制，2分钟内不允许对同一个连续点赞/取消点赞超过 6次
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

}