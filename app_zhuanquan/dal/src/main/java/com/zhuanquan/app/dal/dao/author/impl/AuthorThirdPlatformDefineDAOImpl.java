package com.zhuanquan.app.dal.dao.author.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.AuthorThirdPlatformDefineDAO;

@Repository
public class AuthorThirdPlatformDefineDAOImpl extends BaseDao implements AuthorThirdPlatformDefineDAO {

	@Override
	public List<AuthorThirdPlatformDefine> queryAllInfo() {
		
		
		return this.sqlSessionTemplate.selectList(getSqlName("queryAllInfo"));
	}
	
}