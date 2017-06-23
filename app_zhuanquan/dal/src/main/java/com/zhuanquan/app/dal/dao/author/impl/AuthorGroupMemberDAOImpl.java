package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorGroupMember;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorGroupMemberDAO;


@Repository
public class AuthorGroupMemberDAOImpl extends BaseDao implements AuthorGroupMemberDAO {

	@Override
	public List<AuthorGroupMember> queryGroupMember(long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorGroupMember> queryAuthorGroups(long authorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}