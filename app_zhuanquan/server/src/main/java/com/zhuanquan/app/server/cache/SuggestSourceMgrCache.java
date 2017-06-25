package com.zhuanquan.app.server.cache;

import java.util.List;

import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;

public interface SuggestSourceMgrCache {
	

	/**
	 * 发现页面，获取推荐的资源类型
	 * @return
	 */
	List<WorkSourceTypeInfoBo> getDiscoverSuggestSourceType();
}