package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorBase;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;

@Repository
public class AuthorBaseDAOImpl extends BaseDao implements AuthorBaseDAO {

	@Override
	public AuthorBase queryByAuthorId(long authorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorBase queryByUid(long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorBase> queryByAuthorName(String authorName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
