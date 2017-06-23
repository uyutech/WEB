package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryInfoVo;


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
	List<DiscoveryHotWorkVo> getDiscoverHotWorksByPage(int fromIndex,int limit);
	
	/**
	 * 
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(int fromIndex, int limit);
	
}