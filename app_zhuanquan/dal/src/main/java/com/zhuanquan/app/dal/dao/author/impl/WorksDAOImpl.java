package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.Works;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.WorksDAO;

@Repository
public class WorksDAOImpl extends BaseDao implements WorksDAO {

	@Override
	public Works queryWorkById(long workId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBatchForWorkUpvoteNum(List<BatchUpdateWorkUpvoteNumBo> list) {
		
	}
	
}