package com.zhuanquan.app.dal.dao.author.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zhuanquan.app.common.model.author.AuthorHotIndexes;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorHotIndexesDAO;

@Component
public class AuthorHotIndexesDAOImpl extends BaseDao implements AuthorHotIndexesDAO {

	@Override
	public AuthorHotIndexes queryByAuthorId(long authorId) {
		
		return sqlSessionTemplate.selectOne(getSqlName("queryByAuthorId"), authorId);
		
	}

	@Override
	public List<AuthorHotIndexes> queryHotTopN(int top) {
		
		return sqlSessionTemplate.selectList(getSqlName("getHotTopN"), top);
	}

	@Override
	public List<Long> querySuggestAuthorByPage(List<String> sourceTypes, List<Long> tagIds, int fromIndex, int limit) {

		Map map = new HashMap();
		
		map.put("typeIds", listTransferToString(sourceTypes));
		map.put("tagIds", listToString(tagIds));
		map.put("offset", fromIndex);
		map.put("limit", limit);

		return sqlSessionTemplate.selectList(getSqlName("querySuggestAuthorByPage"), map);
	}
	
}