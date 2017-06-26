package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkInspiration;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkInspirationDAO;

@Repository
public class WorkInspirationDAOImpl extends BaseDao implements WorkInspirationDAO {

	@Override
	public List<WorkInspiration> queryByWorkId(long workId) {
		return this.sqlSessionTemplate.selectList(getSqlName("queryByWorkId"), workId);
	}
	
}