package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkMilestone;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkMilestoneDAO;


@Repository
public class WorkMilestoneDAOImpl extends BaseDao implements WorkMilestoneDAO {

	@Override
	public List<WorkMilestone> queryMileStoneByWorkId(long workId) {
		return sqlSessionTemplate.selectList(getSqlName("queryMileStoneByWorkId"), workId);
	}
	
}