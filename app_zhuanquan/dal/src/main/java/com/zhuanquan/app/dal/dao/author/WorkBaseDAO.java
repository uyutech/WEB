package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;

public interface WorkBaseDAO {
	
	/**
	 * 根据id查询
	 * @param workId
	 * @return
	 */
	WorkBase queryWorkById(long workId);
	
	
	/**
	 * 批量更新作品点赞数
	 * @param list
	 */
	void updateBatchForWorkUpvoteNum(List<BatchUpdateWorkUpvoteNumBo> list);
	
}