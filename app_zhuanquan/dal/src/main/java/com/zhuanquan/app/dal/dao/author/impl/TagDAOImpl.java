package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.TagDAO;

@Repository
public class TagDAOImpl extends BaseDao implements TagDAO {

	@Override
	public List<Tag> queryTagsByIds(List<Long> tagIds) {
		return null;
	}

	@Override
	public Tag queryById(long tagId) {
		return null;
	}

}