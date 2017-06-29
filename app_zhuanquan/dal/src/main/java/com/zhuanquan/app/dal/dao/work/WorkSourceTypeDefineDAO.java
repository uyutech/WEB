package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;

public interface WorkSourceTypeDefineDAO {
	
	
	/**
	 * 查询所有的
	 * @return
	 */
	List<WorkSourceTypeDefine> queryAll();
	
	
	/**
	 * 查询资源类型，及其子类型
	 * 
	 * @param sourceType
	 * @return
	 */
	List<String> querySourceTypeAndSubType(List<String> sourceType);
	
}