package com.zhuanquan.app.server.cache;

import com.zhuanquan.app.common.model.work.WorkBase;

public interface WorksCache {
	
	
	/**
	 * 根据id查询作品
	 * @param workId
	 * @return
	 */
	WorkBase queryWorkById(long workId);
	
	
}