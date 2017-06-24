package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformInfoDAO;

@Repository
public class AuthorThirdPlatformInfoDAOImpl extends BaseDao implements AuthorThirdPlatformInfoDAO {

	@Override
	public List<AuthorThirdPlatformInfo> queryByAuthorId(long authorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}