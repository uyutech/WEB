package com.zhuanquan.app.server.cache;

import com.zhuanquan.app.common.model.author.Works;

public interface WorksCache {
	
	
	/**
	 * 根据id查询作品
	 * @param workId
	 * @return
	 */
	Works queryWorkById(long workId);
	
	
}