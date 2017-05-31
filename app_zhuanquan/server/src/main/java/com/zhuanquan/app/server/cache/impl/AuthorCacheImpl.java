package com.zhuanquan.app.server.cache.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.server.cache.AuthorCache;


@Service
public class AuthorCacheImpl extends BaseDao implements AuthorCache {

	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private AuthorBaseDAO authorBaseDAO;
	
	@Override
	public Map<String, AuthorBase> batchQueryAuthorBaseByIds(List<Long> authorIds) {
		
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
	
	
	Map<String, AuthorBase> resultMap = new HashMap<String, AuthorBase>();
	
		for (int index = 0; index < authorIdsHashKey.size(); index++) {

			String baseStr = authorBaseList.get(index);

			// 表示redis没有，需要懒加载进去
			if (StringUtils.isEmpty(baseStr)) {
				exclude.add(authorIds.get(index));

				// 先设置为null
				resultMap.put(authorIdsHashKey.get(index), null);
			} else {
				resultMap.put(authorIdsHashKey.get(index), JSON.parseObject(baseStr, AuthorBase.class));
			}

		}
//
//		//
//
		//把缓存中缺少的通过这个方式补上去
		if (CollectionUtils.isNotEmpty(exclude)) {
			
			List<AuthorBase> list = authorBaseDAO.queryByAuthorIds(exclude);

			Map<String, String> needCommitMap = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(list)) {

				for (AuthorBase base : list) {
					resultMap.put(base.getAuthorId().toString(), base);
					needCommitMap.put(base.getAuthorId().toString(), JSON.toJSONString(base));
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
	
}