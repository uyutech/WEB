package com.zhuanquan.app.dal.dao.author;

import com.zhuanquan.app.common.model.author.Works;

public interface WorksDAO {
	
	/**
	 * 根据id查询
	 * @param workId
	 * @return
	 */
	Works queryWorkById(long workId);
	
}