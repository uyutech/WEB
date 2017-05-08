package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.WorkBaseDAO;

@Repository
public class WorkBaseDAOImpl extends BaseDao implements WorkBaseDAO {

	@Override
	public WorkBase queryWorkById(long workId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBatchForWorkUpvoteNum(List<BatchUpdateWorkUpvoteNumBo> list) {
		
	}
	
}