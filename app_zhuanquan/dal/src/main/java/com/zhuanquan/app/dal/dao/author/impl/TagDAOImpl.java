package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.Tag;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.TagDAO;

@Repository
public class TagDAOImpl extends BaseDao implements TagDAO {

	@Override
	public List<Tag> queryTagsByIds(List<Long> tagIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag queryById(long tagId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}