package com.zhuanquan.app.server.controller.discovery;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.zhuanquan.app.common.component.cache.redis.lock.RedisSimpleLock;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryQuerySuggestTagRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestSourceTypeVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestTagInfoVo;
import com.zhuanquan.app.dal.dao.work.WorkSourceTypeDefineDAO;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.DiscoveryService;

@Controller
@RequestMapping(value = "/discovery")
public class DiscoveryController extends BaseController {
	
	@Resource
	private DiscoveryService discoveryService;
	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private RedisSimpleLock redisSimpleLock;
	
	
	@Resource
	private WorkSourceTypeDefineDAO  workSourceTypeDefineDAO;

	
	/**
	 * 获取发现页面推荐的 资源类型
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/queryDisSuggestSourceType",produces = {"application/json"})
	@ResponseBody
	public ApiResponse querySuggestSourceType(){
		
		DiscoverySuggestSourceTypeVo vo = discoveryService.querySuggestSourceType();
		
		return ApiResponse.success(vo);
		
	}
	
	
	/**
	 * 获取发现页面推荐的 tag类型
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/queryDisSuggestTag",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryDisSuggestTag(DiscoveryQuerySuggestTagRequest request ){
		
		DiscoverySuggestTagInfoVo vo;
		
		return ApiResponse.success();
		
	}
	
	
	
	/**
	 * 获取发现页面推荐的热点作品
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/pageQueryDiscoverHotWorks",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotWorks(DiscoveryPageQueryRequest request ){
		

		List<DiscoveryHotWorkVo> list;
		
		return ApiResponse.success();
		
	}	
	
	
	
	
	/**
	 * 获取发现页面推荐的作者
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/pageQueryDiscoverHotAuthors",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotAuthors(DiscoveryPageQueryRequest request ){
		

		List<DiscoveryHotAuthorVo> list;
		
		return ApiResponse.success();
	}
	
	
	/**
	 * 获取发现页面，热门专辑
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/pageQueryDiscoverHotAlbums",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotAlbums(DiscoveryPageQueryRequest request ){
		
	
		List<String> list = Lists.newArrayList("100");
		
		
		
		redisHelper.valueSetIfAbsent("test", "test");
		
		redisSimpleLock.tryLock("test", 5, TimeUnit.MINUTES);
		
		
		Object obj = workSourceTypeDefineDAO.querySourceTypeAndSubType(list);
		
		
		return ApiResponse.success();
	}
	
	
	
	
	
}