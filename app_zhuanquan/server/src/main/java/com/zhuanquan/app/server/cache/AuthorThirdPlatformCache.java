package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;

/**
 * 第三方平台的cahche
 * @author zhangjun
 *
 */
public interface AuthorThirdPlatformCache {
	
	/**
	 * 批量查询第三方平台信息
	 * @param platformIds
	 * @return
	 */
	Map<String,AuthorThirdPlatformDefine> batchQueryThirdPlatformInfo(List<Integer> platformIds);
	
	
	
	
}