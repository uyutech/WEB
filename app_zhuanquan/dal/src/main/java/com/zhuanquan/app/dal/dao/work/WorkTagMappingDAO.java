package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkTagMapping;

/**
 * 作品标签
 * @author zhangjun
 *
 */
public interface WorkTagMappingDAO {
	
	
	/**
	 * 批量插入
	 * @param list
	 */
	void insertOrUpdateBatch(List<WorkTagMapping> list);
	
	
	/**
	 * 根据workid查询
	 * @param workId
	 * @return
	 */
	List<WorkTagMapping> queryWorkTags(long workId);
	
}