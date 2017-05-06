package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.work.Works;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;

public interface WorksDAO {
	
	/**
	 * 根据id查询
	 * @param workId
	 * @return
	 */
	Works queryWorkById(long workId);
	
	
	/**
	 * 批量更新作品点赞数
	 * @param list
	 */
	void updateBatchForWorkUpvoteNum(List<BatchUpdateWorkUpvoteNumBo> list);
	
}