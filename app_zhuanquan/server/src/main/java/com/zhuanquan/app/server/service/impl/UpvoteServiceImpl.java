package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.server.cache.UserUpvoteWorkMappingCache;
import com.zhuanquan.app.server.service.UpvoteService;

@Service
public class UpvoteServiceImpl implements UpvoteService {

	@Resource
	private UserUpvoteWorkMappingCache userUpvoteWorkMappingCache;
	
	@Override
	public void upvoteWork(long uid, long workId) {
		userUpvoteWorkMappingCache.upvoteWork(uid, workId);
		
	}

	@Override
	public void cancelUpvoteWork(long uid, long workId) {
		userUpvoteWorkMappingCache.cancelUpvoteWork(uid, workId);
		
	}

	@Override
	public long queryWorkUpvoteNum(long workId) {
		return userUpvoteWorkMappingCache.queryWorkUpvoteNum(workId);
	}

	@Override
	public boolean hasUpvoteWork(long uid, long workId) {

		return userUpvoteWorkMappingCache.hasUpvoteWork(uid, workId);
	}
	
}