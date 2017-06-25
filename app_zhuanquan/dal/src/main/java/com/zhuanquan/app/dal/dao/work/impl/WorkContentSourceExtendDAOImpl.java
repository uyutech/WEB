package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceExtendDAO;


@Repository
public class WorkContentSourceExtendDAOImpl extends BaseDao implements WorkContentSourceExtendDAO {

	@Override
	public void insertOrUpdateBatch(List<WorkContentSourceExtend> list) {
		
		
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatch"), list);
		
		
	}

	@Override
	public List<WorkContentSourceExtend> queryBySourceId(long sourceId) {
		return sqlSessionTemplate.selectList(getSqlName("queryBySourceId"), sourceId);
	}
	
}