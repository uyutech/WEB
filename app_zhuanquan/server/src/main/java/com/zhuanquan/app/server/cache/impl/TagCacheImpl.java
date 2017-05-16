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
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.component.event.redis.CacheChangedListener;
import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;
import com.zhuanquan.app.common.constants.RegisterFlowConstants;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;
import com.zhuanquan.app.dal.dao.author.TagDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.dal.dao.user.impl.UserFollowTagsMappingDAOImpl;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.task.HotTagsUpdateTask;

/**
 * 
 * @author zhangjun
 *
 */
@Repository
public class TagCacheImpl extends CacheChangedListener implements TagCache {


	
	@Resource
	private TagDAO  tagDAO;
	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;
	
	@Resource
	private HotTagsUpdateTask hotTagsUpdateTask;
	
	@Override
	public List<SuggestTagVo> getSuggestTag(int pageNum,int pageSize) {
		
		String key = RedisKeyBuilder.getHotTagsSuggestKey();
		
		String hashKey = pageNum+"_"+pageSize;
		

		//tag 第一页 是 48小时内最热的标签 15条(REG_SUGGEST_TAG_RECENT_NUM_LIMIT)  +  15条 总关注数最多的标签
		//tag 第一页 之后都是按总关注数最多来排行
		//task里的定时任务会缓存1-5页的数据，如果继续下啦，那么就直接从总关注最多的取
		String result = redisHelper.hashGet(key, hashKey);
		
		//

		if(StringUtils.isNotEmpty(result)) {
			return JSON.parseArray(result, SuggestTagVo.class);
		}


		//第5页之后的不保证不重复,
		List<Long> ids = userFollowTagsMappingDAO.queryHotTagsByPage((pageNum-1)*RegisterFlowConstants.REG_SUGGEST_TAG_PAGE_SIZE, RegisterFlowConstants.REG_SUGGEST_TAG_PAGE_SIZE, null);
		
		
		if(CollectionUtils.isEmpty(ids)) {
			return null;
		}
		
		Map<String,Tag> map = this.getTagListByIds(ids);

		List<SuggestTagVo> list =new ArrayList<SuggestTagVo>();
		
		for(Entry<String,Tag> entry:map.entrySet()) {
			Tag tag = entry.getValue();
			
			if(tag!=null) {
				SuggestTagVo record  = new SuggestTagVo();
				
				record.setTagId(tag.getTagId());
				record.setTagName(tag.getTagName());
				record.setTagType(tag.getTagType());
				
				list.add(record);
			}
		}
		
		
		return list.size() == 0?null:list;
	}
	
	
	
	
	
    /**
     * 获取随机的一组key
     * 
     * @param needKeyNum
     * @param totalTagNum
     * @return
     */
	private List<String> getRandomKeys(int needKeyNum,int totalTagNum) {
		
		
		return null;
		
	}
	
	
	

	@Override
	public List<RedisCacheEnum> getMonitorRedisCache() {

		return Lists.newArrayList(RedisCacheEnum.REDIS_CACHE_TAG);
	}

	@Override
	public void doProcessCacheCleanEvent(CacheClearEvent event) {
		
	}





	@Override
	public Map<String,Tag> getTagListByIds(List<Long> tagIds) {
		
		if(CollectionUtils.isEmpty(tagIds)) {
			return null;
		}
		
		
		String key = RedisKeyBuilder.getTagsIdsKey();
		

		List<String> tagIdsHashKey = new ArrayList<String>();
		
		for(Long id:tagIds) {
			tagIdsHashKey.add(id.toString());
		}
		

		List<String> tags =  redisHelper.hashMultiGet(key, tagIdsHashKey);
		

		List<Long> exclude = new ArrayList<Long>();
		
		
		Map<String,Tag> resultMap  = new HashMap<String,Tag>();
		

		for(int index = 0;index <tagIdsHashKey.size(); index ++) {
			
			String tagStr = tags.get(index);
			
			//表示redis没有，需要懒加载进去
			if(StringUtils.isEmpty(tagStr)) {
				exclude.add(tagIds.get(index));
				
				//先设置为null
				resultMap.put(tagIdsHashKey.get(index), null);
			} else {
				resultMap.put(tagIdsHashKey.get(index), JSON.parseObject(tagStr, Tag.class));
			}

		}

		//
		
		if(CollectionUtils.isNotEmpty(exclude)) {
			List<Tag> list = tagDAO.queryTagsByIds(exclude);
			
			Map<String,String> needCommitMap  = new HashMap<String,String>();

			if(CollectionUtils.isNotEmpty(list)) {
				
				for(Tag tag:list) {
					resultMap.put(tag.getTagId().toString(), tag);
					needCommitMap.put(tag.getTagId().toString(), JSON.toJSONString(tag));
				}
				
				//需要回补到缓存中的值
				if(MapUtils.isNotEmpty(needCommitMap)) {
					redisHelper.hashPutAll(key, needCommitMap);
					redisHelper.expire(key, 2, TimeUnit.HOURS);
				}
				
			}

		}
		
		
		
		return resultMap;
	}
	
}