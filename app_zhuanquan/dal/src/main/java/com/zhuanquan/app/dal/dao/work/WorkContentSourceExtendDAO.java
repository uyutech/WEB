package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;

public interface WorkContentSourceExtendDAO{
	
	
	/**
	 * 批量插入
	 * @param list
	 */
	void insertOrUpdateBatch(List<WorkContentSourceExtend> list);
	
	
	/**
	 * 查询资源的扩展属性
	 * @param sourceId
	 * @return
	 */
	List<WorkContentSourceExtend> queryBySourceId(long sourceId);
}