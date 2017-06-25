package com.zhuanquan.app.dal.dao.work.impl;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;

@Repository
public class WorkTagMappingDAOImpl extends BaseDao implements WorkTagMappingDAO {

	@Override
	public void insertOrUpdateBatch(List<WorkTagMapping> list) {
		
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatch"), list);
	}

	@Override
	public List<WorkTagMapping> queryWorkTags(long workId) {
		return sqlSessionTemplate.selectList(getSqlName("queryWorkTags"), workId);
	}
	
}