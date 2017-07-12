package com.zhuanquan.app.server.cache.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.model.author.AuthorGroupBase;
import com.zhuanquan.app.common.model.author.AuthorGroupMember;
import com.zhuanquan.app.dal.dao.author.AuthorGroupBaseDAO;
import com.zhuanquan.app.dal.dao.author.AuthorGroupMemberDAO;
import com.zhuanquan.app.server.cache.AuthorGroupCache;

@Service
public class AuthorGroupCacheImpl extends CacheChangedListener implements AuthorGroupCache {

	@Resource
	private AuthorGroupBaseDAO authorGroupBaseDAO;

	@Resource
	private AuthorGroupMemberDAO authorGroupMemberDAO;

	@Resource
	private RedisHelper redisHelper;

	@Override
	public AuthorGroupBase queryGroupBase(long groupId) {

		String key = RedisKeyBuilder.getAuthorGroupBaseInfoKey(groupId);

		String val = redisHelper.valueGet(key);

		if (val != null) {
			return JSON.parseObject(val, AuthorGroupBase.class);
		}

		AuthorGroupBase base = authorGroupBaseDAO.queryById(groupId);

		if (base == null) {
			return null;
		}

		redisHelper.valueSet(key, JSON.toJSONString(base), 30, TimeUnit.MINUTES);
		return base;
	}

	@Override
	public List<AuthorGroupBase> queryAuthorGroup(long authorId) {

		List<AuthorGroupMember> members = authorGroupMemberDAO.queryAuthorGroups(authorId);

		if (CollectionUtils.isEmpty(members)) {
			return null;
		}

		List<AuthorGroupBase> list = new ArrayList<AuthorGroupBase>();

		for (AuthorGroupMember member : members) {
			list.add(queryGroupBase(member.getGroupId()));
		}

		return list;
	}



	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {
		return null;
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {

	}

}