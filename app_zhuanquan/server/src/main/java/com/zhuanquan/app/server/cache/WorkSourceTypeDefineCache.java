package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;

public interface WorkSourceTypeDefineCache {
	
	
	/**
	 * 批量查询资源的类型定义
	 * @param sourceTypes
	 * @return
	 */
	Map<String,WorkSourceTypeDefine> batchQueryTypes(List<String> sourceTypes);
	
}