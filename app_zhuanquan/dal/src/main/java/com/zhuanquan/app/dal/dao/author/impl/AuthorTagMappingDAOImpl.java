package com.zhuanquan.app.dal.dao.author.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.zhuanquan.app.common.model.author.AuthorTagMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorTagMappingDAO;

@Repository
public class AuthorTagMappingDAOImpl extends BaseDao implements AuthorTagMappingDAO {

	@Override
	public List<AuthorTagMapping> queryByAuthorIds(List<Long> authorIds) {

		String ids = listToString(authorIds);

		Map map = new HashMap();
		map.put("ids", ids);

		return sqlSessionTemplate.selectList(getSqlName("queryByAuthorIds"), map);
	}

	@Override
	public List<AuthorTagMapping> queryByAuthorId(long authorId) {

		return queryByAuthorIds(Lists.newArrayList(authorId));
	}

}