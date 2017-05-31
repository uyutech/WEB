package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

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
	
}