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
import com.zhuanquan.app.common.view.vo.work.WorkDetailInfoVo;

public interface WorksCache {

	/**
	 * 根据id查询作品
	 * 
	 * @param workId
	 * @return
	 */
	WorkBase queryWorkById(long workId);

	/**
	 * 查询基本信息
	 * @param workId
	 * @return
	 */
	WorkBase lazyFetchWorkBaseCache(long workId);

	/**
	 * 查询作品的扩展信息
	 * @param workId
	 * @return
	 */
	Map<String, List<WorkBaseExtend>> lazyFetchWorkBaseExtendInfo(long workId);

	/**
	 * 查询作品参与人信息，以 rolecode为维度
	 * @param workId
	 * @return key为rolecode，value 为对应角色下的list列表
	 */
	Map<String, List<WorkAttender>> lazyFetchWorkAttenderCache(long workId);

	/**
	 * 查询作品包含的资源，以资源类型为维度
	 * @param workId
	 * @return  map的key为sourcecategory，value是这个类别下的资源list
	 */
	Map<String, List<WorkContentSource>> lazyFetchWorkContentSourceCache(long workId);
	
	/**
	 * 查询作品的所有tag
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
	 * discovery 分页查询热点作品
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotWorkVo> queryDiscoverHotWorksByPage(DiscoveryPageQueryRequest request);
	
	
	/**
	 * 作品详情
	 * @param workId
	 * @return
	 */
	WorkDetailInfoVo queryWorkDetail(long workId);
	
	

}