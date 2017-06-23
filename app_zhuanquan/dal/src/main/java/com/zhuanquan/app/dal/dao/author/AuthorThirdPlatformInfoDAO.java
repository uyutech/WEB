package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;

/**
 * 
 * @author zhangjun
 *
 */
public interface AuthorThirdPlatformInfoDAO {
	
	
	/**
	 * 查询
	 * @param authorId
	 * @return
	 */
	List<AuthorThirdPlatformInfo> queryByAuthorId(long authorId);
	
}