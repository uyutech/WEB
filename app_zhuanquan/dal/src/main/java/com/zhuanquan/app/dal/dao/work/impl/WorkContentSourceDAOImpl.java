package com.zhuanquan.app.dal.dao.work.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceDAO;

@Repository
public class WorkContentSourceDAOImpl extends BaseDao implements WorkContentSourceDAO {

	@Override
	public long insertOrUpdateRecord(WorkContentSource source) {
		return this.sqlSessionTemplate.insert(getSqlName("insertOrUpdateRecord"), source);
	}

	@Override
	public List<WorkContentSource> queryByWorkId(long workId) {
		return sqlSessionTemplate.selectList(getSqlName("queryByWorkId"), workId);
	}
	
}