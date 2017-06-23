package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkHotIndex;

/**
 * 热点作品
 * @author zhangjun
 *
 */
public interface WorkHotIndexDAO {
	
	/**
	 * 查询top n的数据
	 * @param top
	 * @return
	 */
	List<WorkHotIndex> queryTopN(int top);
	
}