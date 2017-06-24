package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkBaseExtend;
import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;

public interface WorksCache {

	/**
	 * 根据id查询作品
	 * 
	 * @param workId
	 * @return
	 */
	WorkBase queryWorkById(long workId);

	/**
	 * 
	 * @param workId
	 * @return
	 */
	WorkBase lazyFetchWorkBaseCache(long workId);

	/**
	 * 
	 * @param workId
	 * @return
	 */
	Map<String, List<WorkBaseExtend>> lazyFetchWorkBaseExtendInfo(long workId);

	/**
	 * 
	 * @param workId
	 * @return
	 */
	Map<String, List<WorkAttender>> lazyFetchWorkAttenderCache(long workId);

	/**
	 * 
	 * @param workId
	 * @return
	 */
	Map<String, List<WorkContentSource>> lazyFetchWorkContentSourceCache(long workId);
	
	/**
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkTagMapping> lazyFetchWorkTags(long workId);

	/**
	 * 
	 * @param sourceId
	 * @return
	 */
	Map<String, List<WorkContentSourceExtend>> lazyFetchWorkContentSourceExtendCache(long sourceId);
	
	
	/**
	 * discovery 分页查询
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotWorkVo> queryDiscoverHotWorksByPage(DiscoveryPageQueryRequest request);

}