package com.zhuanquan.app.server.controller.discovery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.redis.lock.RedisSimpleLock;
import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkAlbumVo;
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
	private WorkSourceTypeDefineDAO workSourceTypeDefineDAO;

	/**
	 * 获取发现页面推荐的 资源类型
	 * 
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value = "/queryDisSuggestSourceType", produces = { "application/json" })
	@ResponseBody
	public ApiResponse querySuggestSourceType() {

		DiscoverySuggestSourceTypeVo vo = discoveryService.querySuggestSourceType();

		return ApiResponse.success(vo);

	}

	/**
	 * 获取发现页面推荐的 tag类型
	 * 
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value = "/queryDisSuggestTag", produces = { "application/json" })
	@ResponseBody
	public ApiResponse queryDisSuggestTag(String reqJson) {

		DiscoveryQuerySuggestTagRequest request = JSON.parseObject(reqJson, DiscoveryQuerySuggestTagRequest.class);

		DiscoverySuggestTagInfoVo vo = discoveryService.queryDiscoverSuggestTags(request);

		return ApiResponse.success(vo);

	}

	/**
	 * 获取发现页面推荐的热点作品
	 * 
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value = "/pageQueryDiscoverHotWorks", produces = { "application/json" })
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotWorks(String reqJson) {

		DiscoveryPageQueryRequest request = JSON.parseObject(reqJson, DiscoveryPageQueryRequest.class);

		List<DiscoveryHotWorkVo> list = discoveryService.getDiscoverHotWorksByPage(request);

		return ApiResponse.success(list);

	}

	/**
	 * 获取发现页面推荐的作者
	 * 
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value = "/pageQueryDiscoverHotAuthors", produces = { "application/json" })
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotAuthors(String reqJson) {

		DiscoveryPageQueryRequest request = JSON.parseObject(reqJson, DiscoveryPageQueryRequest.class);

		List<DiscoveryHotAuthorVo> list = discoveryService.getDiscoverHotAuthorByPage(request);

		return ApiResponse.success(list);
	}

	/**
	 * 获取发现页面，热门专辑
	 * 
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value = "/pageQueryDiscoverHotAlbums", produces = { "application/json" })
	@ResponseBody
	public ApiResponse pageQueryDiscoverHotAlbums(String reqJson) {

		DiscoveryPageQueryRequest request = JSON.parseObject(reqJson, DiscoveryPageQueryRequest.class);

		List<DiscoveryHotWorkAlbumVo> list = discoveryService.getDiscoverHotWorkAlbumByPage(request);

		return ApiResponse.success(list);
	}

}