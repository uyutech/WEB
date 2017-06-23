package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceExtendDAO;


@Repository
public class WorkContentSourceExtendDAOImpl extends BaseDao implements WorkContentSourceExtendDAO {

	@Override
	public void insertOrUpdateBatch(List<WorkContentSourceExtend> list) {
		
	}

	@Override
	public List<WorkContentSourceExtend> queryBySourceId(long sourceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}