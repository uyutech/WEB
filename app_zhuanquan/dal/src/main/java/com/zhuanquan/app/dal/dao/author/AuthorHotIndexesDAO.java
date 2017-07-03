package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorHotIndexes;

/**
 * 热度指数
 * @author zhangjun
 *
 */
public interface AuthorHotIndexesDAO {
	
	/**
	 * 根据id查询
	 * @param authorId
	 * @return
	 */
	AuthorHotIndexes queryByAuthorId(long authorId);
	
	
	/**
	 * 查询热度 top n的
	 * @param top
	 * @return
	 */
	List<AuthorHotIndexes> queryHotTopN(int top);
	
	
	/**
	 * 发现页面查询推荐的作者
	 * @param sourceTypes
	 * @param tagIds
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<Long> querySuggestAuthorByPage(List<String> sourceTypes,List<Long> tagIds,int fromIndex,int limit);
	
}