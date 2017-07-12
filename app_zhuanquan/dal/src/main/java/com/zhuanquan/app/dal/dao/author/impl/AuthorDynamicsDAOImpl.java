package com.zhuanquan.app.dal.dao.author.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorDynamics;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorDynamicsDAO;

@Repository
public class AuthorDynamicsDAOImpl extends BaseDao implements AuthorDynamicsDAO {

	@Override
	public List<AuthorDynamics> queryAuthorDynamicsByPage(List<Long> authorIds, int offset, int limit) {

		Map map = new HashMap();
		
		map.put("ids", this.listToString(authorIds));
		map.put("offset", offset);
		map.put("limit", limit);

		
		
		return this.sqlSessionTemplate.selectList(getSqlName("queryAuthorDynamicsByPage"), map);
	}
	
}