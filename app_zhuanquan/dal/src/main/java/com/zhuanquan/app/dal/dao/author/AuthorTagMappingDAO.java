package com.zhuanquan.app.dal.dao.author;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.zhuanquan.app.common.model.author.AuthorTagMapping;

/**
 * 
 * @author zhangjun
 *
 */
public interface AuthorTagMappingDAO {
	
	
	/**
	 * 根据 authorids查询
	 * @param authorIds
	 * @return
	 */
	List<AuthorTagMapping> queryByAuthorIds(List<Long> authorIds);
	
	/**
	 * 查询单个作者的
	 * @param authorId
	 * @return
	 */
	List<AuthorTagMapping> queryByAuthorId(long authorId);
	
//	
//	/**
//	 * 根据 作者id 查询出 string 格式的，pair左边是 tagstr，右边是id的str格式，
//	 * 
//	 * 比如 左边是  歌手,鼓手    右边是 112,113
//	 * @param authorId
//	 * @return
//	 */
//	Pair<String, String> queryTagStrByAuthorId(long authorId);
//	
//	
//	/**
//	 * 查询结果是个map结构的，pair左边是 tagstr，右边是id的str格式，比如 左边是  歌手,鼓手    右边是 112,113
//	 * @param authorIds
//	 * @return
//	 */
//	Map<String,Pair<String, String>> queryTagStrByAuthorIds(List<Long> authorIds);
//	
}