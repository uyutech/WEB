package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.author.AuthorBase;

public interface AuthorCache {
	
	/**
	 * 批量查询作者信息
	 * @param authorIds
	 * @return
	 */
	Map<String, AuthorBase> batchQueryAuthorBaseByIds(List<Long> authorIds);
	
}