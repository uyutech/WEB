package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkBaseExtend;

public interface WorkBaseExtendDAO {
	
	
	/**
	 * 查询扩展信息
	 * @param workId
	 * @return
	 */
	List<WorkBaseExtend> queryExtendInfoByWorkId(long workId);
	
}