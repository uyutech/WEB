package com.zhuanquan.app.dal.dao.author.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorExtendInfo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorExtendInfoDAO;

@Repository
public class AuthorExtendInfoDAOImpl extends BaseDao implements AuthorExtendInfoDAO {

	@Override
	public List<AuthorExtendInfo> queryByAuthorId(long authorId) {
		return this.sqlSessionTemplate.selectList(getSqlName("queryByAuthorId"), authorId);
	}
	
}