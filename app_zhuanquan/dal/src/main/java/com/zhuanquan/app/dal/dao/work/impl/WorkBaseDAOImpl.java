package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.view.bo.AsyncUpdateUnitBo;
import com.zhuanquan.app.common.view.bo.BatchUpdateWorkUpvoteNumBo;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;

@Repository
public class WorkBaseDAOImpl extends BaseDao  implements WorkBaseDAO {

	@Override
	public long insertWorkBaseInfo(WorkBase base) {

		this.sqlSessionTemplate.insert(getSqlName("insertWorkBaseInfo"), base);
		
		return base.getWorkId();
	}

	@Override
	public WorkBase queryWorkById(long workId) {
		
		return sqlSessionTemplate.selectOne(getSqlName("queryWorkById"), workId);
	}

	@Override
	public void updateBatchForWorkUpvote(List<BatchUpdateWorkUpvoteNumBo> list) {
		
		
		
	}

	@Override
	public void updateBatchToIncrseaseWorkUpvoteNum(List<AsyncUpdateUnitBo> list) {
		
	}
	
}