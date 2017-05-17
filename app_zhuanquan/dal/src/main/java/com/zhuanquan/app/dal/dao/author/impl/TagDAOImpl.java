package com.zhuanquan.app.dal.dao.author.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.TagDAO;

@Repository
public class TagDAOImpl extends BaseDao implements TagDAO {

	@Override
	public List<Tag> queryTagsByIds(List<Long> tagIds) {
		
		
		String ids = listToString(tagIds);
		
		Map map = new HashMap();
		map.put("ids", ids);
		return sqlSessionTemplate.selectList(getSqlName("queryTagsByIds"), map);
	}

	@Override
	public Tag queryById(long tagId) {
		
		
		return sqlSessionTemplate.selectOne(getSqlName("queryById"), tagId);
	}

	

}
