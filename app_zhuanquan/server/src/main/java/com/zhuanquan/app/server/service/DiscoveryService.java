package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryInfoVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryQuerySuggestTagRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestSourceTypeVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestTagInfoVo;


/**
 * 查询
 * @author zhangjun
 *
 */
public interface DiscoveryService {
	

	
	/**
	 * 分页查询热点作品
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotWorkVo> getDiscoverHotWorksByPage(DiscoveryPageQueryRequest request);
	
	/**
	 * 
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(DiscoveryPageQueryRequest request);
	
	
	
	/**
	 * 获取发现页面，推荐的 可选资源类型
	 * @return
	 */
	DiscoverySuggestSourceTypeVo querySuggestSourceType();
	
	/**
	 * 根据选择的资源类型，查询tag
	 * @param request
	 * @return
	 */
	DiscoverySuggestTagInfoVo queryDiscoverSuggestTags(DiscoveryQuerySuggestTagRequest request);
	
}