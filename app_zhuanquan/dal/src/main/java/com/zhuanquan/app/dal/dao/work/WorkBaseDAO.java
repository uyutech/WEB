package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.view.bo.AsyncUpdateUnitBo;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;

/**
 * 
 * @author zhangjun
 *
 */
public interface WorkBaseDAO {
	
	
	/**
	 * 插入作品基本信息
	 * @param base
	 * @return
	 */
	long insertWorkBaseInfo(WorkBase base);
	
	
	/**
	 * 根据id查询
	 * @param workId
	 * @return
	 */
	WorkBase queryWorkById(long workId);
	
	
	/**
	 * 批量更新作品点赞数，直接替换原来的总数量
	 * @param list
	 */
	void updateBatchForWorkUpvote(List<BatchUpdateWorkUpvoteNumBo> list);
	
	/**
	 * 批量增加点赞数，在原来基础上加
	 * @param list
	 */
	void updateBatchToIncrseaseWorkUpvoteNum(List<AsyncUpdateUnitBo> list);
	
	
	/**
	 * 分页查询作品信息
	 * @param workIds
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<WorkBase> queryWorksInfoByPage(List<Long> workIds,int fromIndex,int limit);
	
	
	
	
	
}