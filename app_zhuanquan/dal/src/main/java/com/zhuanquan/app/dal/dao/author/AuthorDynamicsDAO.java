package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorDynamics;

/**
 * 
 * @author zhangjun
 *
 */
public interface AuthorDynamicsDAO {
	
	
	/**
	 * 分页查询
	 * @param authorIds
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<AuthorDynamics> queryAuthorDynamicsByPage(List<Long> authorIds,int offset ,int limit );
	
	
}