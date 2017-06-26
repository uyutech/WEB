package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkMilestone;

/**
 * 作品里程碑
 * @author zhangjun
 *
 */
public interface WorkMilestoneDAO {
	
	/**
	 * 查询作品的里程碑记录
	 * @param workId
	 * @return
	 */
	List<WorkMilestone> queryMileStoneByWorkId(long workId);
	
}