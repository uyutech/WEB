package com.zhuanquan.app.dal.dao.work.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkHotIndexDAO;


@Repository
public class WorkHotIndexDAOImpl extends BaseDao implements WorkHotIndexDAO {

	@Override
	public List<WorkHotIndex> queryTopN(int top) {
		
		
		
		
		return null;
	}
	
}