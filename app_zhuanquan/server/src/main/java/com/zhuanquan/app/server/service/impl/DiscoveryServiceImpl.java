package com.zhuanquan.app.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;
import com.zhuanquan.app.server.cache.WorksCache;
import com.zhuanquan.app.server.service.DiscoveryService;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

	@Resource
	private WorksCache worksCache;
	
	@Resource
	private AuthorCache authorCache;
	
	@Resource
	private AuthorHotIndexesCache authorHotIndexesCache;
	

	/**
	 * 分页查询
	 * @param fromIndex 从第几个开始
	 * @param limit
	 * @return
	 */
	@Override
	public List<DiscoveryHotWorkVo> getDiscoverHotWorksByPage(DiscoveryPageQueryRequest request){
		
		return worksCache.queryDiscoverHotWorksByPage(request);
	
	}
	
	
	/**
	 * 获取热度 top n的 信息，分页查询
	 * @param num
	 * @return
	 */
	@Override
	public List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(DiscoveryPageQueryRequest request){

		return authorCache.getDiscoverHotAuthorByPage(request);
	}
	
	
	
}