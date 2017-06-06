package com.zhuanquan.app.server.cache.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.common.model.author.AuthorTagMapping;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.dal.dao.author.AuthorTagMappingDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.TagCache;


@Service
public class AuthorCacheImpl extends BaseDao implements AuthorCache {

	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private AuthorBaseDAO authorBaseDAO;
	
	@Resource
	private AuthorTagMappingDAO authorTagMappingDAO;
	
	@Resource
	private TagCache tagCache;
	
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
//		//
//
		//把缓存中缺少的通过这个方式补上去
		if (CollectionUtils.isNotEmpty(exclude)) {
			
			List<AuthorBase> list = authorBaseDAO.queryByAuthorIds(exclude);

			
			

			Map<String, String> needCommitMap = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(list)) {

				
				Map<String,Pair<String, String>> map = generateTagStrMap(exclude);
				
				for (AuthorBase base : list) {
					
					// 

					AuthorBaseInfoBo bo = new AuthorBaseInfoBo();
					bo.setAuthorId(base.getAuthorId());
					bo.setAuthorName(base.getAuthorName());
					bo.setHeadUrl(base.getHeadUrl());
					
					Pair<String, String> pair = map.get(base.getAuthorId().toString());
					if(pair!=null) {
					    bo.setTagDesc(pair.getLeft());
					    bo.setTagIds(pair.getRight());
					}
					
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
	private Map<String,Pair<String, String>> generateTagStrMap(List<Long> authorIds) {
		
		Map<String,Pair<String, String>> map = new HashMap<String,Pair<String, String>>();
		
		//order by  authorid，ordernum,create_time
		List<AuthorTagMapping> tagMapList =  authorTagMappingDAO.queryByAuthorIds(authorIds);

		if(CollectionUtils.isEmpty(tagMapList)) {
			return map;
		}
		
		
		
		for(AuthorTagMapping record:tagMapList) {
			
			long tagId = record.getTagId();
			
		     Tag tag = tagCache.getTagById(tagId);
		     if(tag == null){
		    	 continue;
		     }
			
		     
			Pair<String, String> pair = map.get(record.getAuthorId().toString());
			
			if(pair == null) {

			     map.put(record.getAuthorId().toString(), Pair.of(tag.getTagName(), tag.getTagId().toString()));
			     
			} else {

				String tagDesc = pair.getLeft()+","+tag.getTagName();
				
				String tagIdStr = pair.getRight()+","+tag.getTagId().toString();
				
				map.put(record.getAuthorId().toString(), Pair.of(tagDesc, tagIdStr));
			     
			}
		}
		

		return map;
		
	}
}