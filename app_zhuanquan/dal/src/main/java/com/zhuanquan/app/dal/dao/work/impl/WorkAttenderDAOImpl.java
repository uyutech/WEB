package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkAttenderDAO;

@Repository
public class WorkAttenderDAOImpl extends BaseDao implements WorkAttenderDAO{

	
	
	@Override
	public void insertOrUpdateBatch(List<WorkAttender> list) {
		
		
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatch"), list);
	}

	@Override
	public List<WorkAttender> queryWorkAttender(long workId) {
		
		
		return sqlSessionTemplate.selectList(getSqlName("queryWorkAttender"), workId);
	}
	
}