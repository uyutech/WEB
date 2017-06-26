package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkInspiration;

/**
 * 
 * @author zhangjun
 *
 */
public interface WorkInspirationDAO {
	
	/**
	 * 查询作品的创作灵感
	 * @param workId
	 * @return
	 */
	List<WorkInspiration> queryByWorkId(long workId);
	
	
}