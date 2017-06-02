package com.zhuanquan.app.server.thread;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.RedisKeyBuilder;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.common.utils.BeanManager;
import com.zhuanquan.app.common.view.bo.ThirdChannelSyncFollowAuthorRequestBo;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;
import com.zhuanquan.app.server.openapi.OpenApiService;

public class ThirdChannelSyncRunnable implements Runnable {

	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	
	/**
	 * request
	 */
	private ThirdChannelSyncFollowAuthorRequestBo request;
	
	
	private OpenApiService openApiService;
	
	private VipAuthorOpenAccountMappingDAO vipAuthorOpenAccountMappingDAO;

	private RedisHelper redisHelper;
	

	public ThirdChannelSyncRunnable(ThirdChannelSyncFollowAuthorRequestBo request) {

		this.request = request;
		
		openApiService = (OpenApiService) BeanManager.getBeanByName("openApiService");
		
		Assert.notNull(openApiService);

		vipAuthorOpenAccountMappingDAO =  (VipAuthorOpenAccountMappingDAO) BeanManager.getBeanByName("vipAuthorOpenAccountMappingDAO");
		
		Assert.notNull(vipAuthorOpenAccountMappingDAO);
		
		
		redisHelper = (RedisHelper) BeanManager.getBeanByName("redisHelper"); 
		
		Assert.notNull(redisHelper);

	}

	@Override
	public void run() {

		try {
			execute(request);
		} catch (Exception e) {

			logger.error("ThirdChannelSyncRunnable execute sync thread failed,[request]:"+JSON.toJSONString(request), e);
		}

	}

	/**
	 * 执行job
	 * 
	 * @param request
	 */
	private void execute(ThirdChannelSyncFollowAuthorRequestBo request) {


		
		List<String> openIds = openApiService.getAllFollowedAuthorOpenIds(request.getToken(), request.getOpenId(), request.getChannelType());
		
		if(CollectionUtils.isEmpty(openIds)) {
			return;
		}
		
		//其他平台上关注的作者，在我们平台也有的记录
		List<VipAuthorOpenAccountMapping> vipAuthorList = vipAuthorOpenAccountMappingDAO.queryRecordListByOpenIds(openIds, request.getChannelType());

		//key 
		String key = RedisKeyBuilder.getOpenAccountSyncFollowAuthorKey(request.getChannelType(),request.getUid());
		
		// 同步过来之后方到缓存里，放15分钟
		redisHelper.valueSet(key, JSON.toJSONString(vipAuthorList), 15, TimeUnit.MINUTES);
		
	}
	
	

	

}