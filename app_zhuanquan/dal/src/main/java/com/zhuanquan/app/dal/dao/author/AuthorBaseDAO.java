package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorBase;

public interface AuthorBaseDAO {
	
	
	/**
	 * 根据 author id查询
	 * @param authorId
	 * @return
	 */
	AuthorBase queryByAuthorId(long authorId);

	
	/**
	 * 根据作者名查询
	 * @param authorName
	 * @return
	 */
	List<AuthorBase> queryByAuthorName(String authorName);
	
	
}