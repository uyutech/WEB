package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.work.WorkRoleDefine;

public interface WorkRoleDefineCache {
	
	/**
	 * 批量查询
	 * @param roleCodes
	 * @return
	 */
	Map<String,WorkRoleDefine> batchQueryRoleDefine(List<String> roleCodes);
	
	/**
	 * 单个查询
	 * @param roleCode
	 * @return
	 */
	WorkRoleDefine queryRoleDefine(String roleCode);
	
}