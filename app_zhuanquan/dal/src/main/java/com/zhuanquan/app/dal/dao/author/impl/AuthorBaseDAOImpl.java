package com.zhuanquan.app.dal.dao.author.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;

@Repository
public class AuthorBaseDAOImpl extends BaseDao implements AuthorBaseDAO {

	@Override
	public AuthorBase queryByAuthorId(long authorId) {
		return sqlSessionTemplate.selectOne(getSqlName("queryByAuthorId"), authorId);
	}

	@Override
	public List<AuthorBase> queryByAuthorName(String authorName) {
		return sqlSessionTemplate.selectList(getSqlName("queryByAuthorName"), authorName);
	}

	@Override
	public List<AuthorBase> queryByAuthorIds(List<Long> authorIds) {
		
		Map map = new HashMap();
		map.put("ids", listToString(authorIds));		
		
		return sqlSessionTemplate.selectList(getSqlName("queryByAuthorIds"), map);
	}
	
}
