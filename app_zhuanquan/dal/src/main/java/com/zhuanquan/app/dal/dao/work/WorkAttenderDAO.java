package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkAttender;

/**
 * 作品参与者
 * @author zhangjun
 *
 */
public interface WorkAttenderDAO {
	
	/**
	 * 批量处理
	 * @param list
	 */
	void insertOrUpdateBatch(List<WorkAttender> list);
	
	/**
	 * 查询作品参与者
	 * @param workId
	 * @return
	 */
	List<WorkAttender> queryWorkAttender(long workId);
	
}