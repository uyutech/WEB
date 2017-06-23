package com.zhuanquan.app.server.cache.impl;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.constants.AsyncUpdateType;
import com.zhuanquan.app.common.model.user.UserUpvoteWorkMapping;
import com.zhuanquan.app.common.view.bo.AsyncUpdateUnitBo;
import com.zhuanquan.app.dal.dao.user.UserUpvoteWorkMappingDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;
import com.zhuanquan.app.server.cache.AsyncUpdateHandler;
import com.zhuanquan.app.server.cache.UserUpvoteWorkMappingCache;


@Repository
public class UserUpvoteWorkMappingCacheImpl extends AsyncUpdateHandler implements UserUpvoteWorkMappingCache {

	@Resource
	private UserUpvoteWorkMappingDAO userUpvoteWorkMappingDAO;
	
	@Resource
	private WorkBaseDAO workBaseDAO;
	
	
	@Override
	protected AsyncUpdateType getAsyncUpdateType() {

		return AsyncUpdateType.ASYNC_TYPE_UPVOTE;
	}

	@Override
	protected boolean queryIsEnableFromDB(long uid, long targetId) {
	
		UserUpvoteWorkMapping record = userUpvoteWorkMappingDAO.queryUserUpvoteWorkMapping(uid, targetId);

		// 是否已点赞
		boolean isUpvote = (record != null && record.getStatus() == UserUpvoteWorkMapping.STAT_ENABLE);
		
		
		return isUpvote;
	}

	@Override
	protected void doEnableOrDisableToDb(long uid, long targetId, boolean isEnable) {
		
		UserUpvoteWorkMapping record = new UserUpvoteWorkMapping();
		record.setStatus(isEnable?UserUpvoteWorkMapping.STAT_ENABLE:UserUpvoteWorkMapping.STAT_DISABLE);
		record.setWorkId(targetId);
		record.setUid(uid);
		
		List<UserUpvoteWorkMapping> records = Lists.newArrayList(record);
		userUpvoteWorkMappingDAO.insertOrUpdateBatchUserUpvoteWorkMapping(records);
		
	}

	@Override
	protected void doBatchUpdateTotalNumForTargetIds(List<AsyncUpdateUnitBo> list) {
		
		// 批量增加点赞数
		workBaseDAO.updateBatchToIncrseaseWorkUpvoteNum(list);
	}

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {
		
		return null;
	}

	
	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {
		
	}

	@Override
	public void upvoteWork(long uid, long workId) {
		this.enableOrDisable(uid, workId, true);

	}

	@Override
	public void cancelUpvoteWork(long uid, long workId) {
		
		this.enableOrDisable(uid, workId, false);
	}

//	@Override
//	public long queryWorkUpvoteNum(long workId) {
//
//		
//
//		// 增加或者减少关注数，点赞数，评论数等,不保证强一致性
//		String targetChangeKey = getTargetIdIncreaseOrDecreaseKey(workId);
//
//		
//		return 0;
//	}

	@Override
	public boolean hasUpvoteWork(long uid, long workId) {
		return this.queryIsEnableByCache(uid, workId);
	}

	@Override
	public void doPersistUpvoteNumTask() {
		
		this.persistCacheToDbForTotalNum();
	}
	
}