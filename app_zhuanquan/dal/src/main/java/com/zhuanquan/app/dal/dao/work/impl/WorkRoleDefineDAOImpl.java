package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkRoleDefine;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkRoleDefineDAO;

@Repository
public class WorkRoleDefineDAOImpl extends BaseDao implements WorkRoleDefineDAO {

	@Override
	public List<WorkRoleDefine> queryAll() {
		return this.sqlSessionTemplate.selectList(getSqlName("queryAll"));
	}
	
}