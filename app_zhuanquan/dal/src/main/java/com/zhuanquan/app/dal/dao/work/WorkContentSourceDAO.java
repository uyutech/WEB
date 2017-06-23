package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkContentSource;

/**
 * 作品内容
 * @author zhangjun
 *
 */
public interface WorkContentSourceDAO {
	
	/**
	 * 插入
	 * @param source
	 * @return
	 */
	long insertOrUpdateRecord(WorkContentSource source);
	
	
	/**
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkContentSource> queryByWorkId(long workId);
}