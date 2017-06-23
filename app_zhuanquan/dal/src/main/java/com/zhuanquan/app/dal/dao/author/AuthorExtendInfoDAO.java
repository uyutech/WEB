package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorExtendInfo;

public interface AuthorExtendInfoDAO {
	
	
	/**
	 * 根据作者id查询
	 * @param authorId
	 * @return
	 */
	List<AuthorExtendInfo> queryByAuthorId(long authorId);
}