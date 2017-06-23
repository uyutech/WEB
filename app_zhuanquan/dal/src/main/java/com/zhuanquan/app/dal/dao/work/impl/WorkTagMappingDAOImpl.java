package com.zhuanquan.app.dal.dao.work.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;

@Repository
public class WorkTagMappingDAOImpl extends BaseDao implements WorkTagMappingDAO {

	@Override
	public void insertOrUpdateBatch(List<WorkTagMapping> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WorkTagMapping> queryWorkTags(long workId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}