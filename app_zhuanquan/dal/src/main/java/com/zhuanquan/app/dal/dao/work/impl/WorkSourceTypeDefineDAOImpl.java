package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkSourceTypeDefine;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkSourceTypeDefineDAO;

@Repository
public class WorkSourceTypeDefineDAOImpl extends BaseDao implements WorkSourceTypeDefineDAO {

	@Override
	public List<WorkSourceTypeDefine> queryAll() {

		return this.sqlSessionTemplate.selectList(getSqlName("queryAll"));
	}

	@Override
	public List<String> querySourceTypeAndSubType(List<String> sourceTypes) {

		return sqlSessionTemplate.selectList(getSqlName("querySourceTypeAndSubType"), sourceTypes);
	}

}