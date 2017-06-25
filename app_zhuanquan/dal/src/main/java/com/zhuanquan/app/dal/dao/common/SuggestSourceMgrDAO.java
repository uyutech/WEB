package com.zhuanquan.app.dal.dao.common;

import java.util.List;

public interface SuggestSourceMgrDAO {
	
	
	/**
	 * 获取当前推荐的资源类型
	 * @return
	 */
	List<String> queryCurrentSuggestRecord();
	
	 
	
}