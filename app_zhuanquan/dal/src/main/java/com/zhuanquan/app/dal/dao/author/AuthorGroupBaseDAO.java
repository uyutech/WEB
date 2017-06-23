package com.zhuanquan.app.dal.dao.author;

import com.zhuanquan.app.common.model.author.AuthorGroupBase;

public interface AuthorGroupBaseDAO {
	
	/**
	 * 查询group信息
	 * @param groupId
	 * @return
	 */
	AuthorGroupBase queryById(long groupId);
	
	

}