package com.zhuanquan.app.server.cache;

import java.util.List;

import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryQuerySuggestTagRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestTagInfoVo;

public interface SuggestSourceMgrCache {
	

	/**
	 * 发现页面，获取推荐的资源类型
	 * @return
	 */
	List<WorkSourceTypeInfoBo> getDiscoverSuggestSourceType();
	
	
	/**
	 *  根据选择的资源类型，选择标签
	 * @param sourceTypes
	 * @return
	 */
	DiscoverySuggestTagInfoVo queryDiscoverSuggestTags(DiscoveryQuerySuggestTagRequest request);
}