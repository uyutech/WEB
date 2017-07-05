package com.zhuanquan.app.dal.dao.work.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkHotIndex;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkHotIndexDAO;


@Repository
public class WorkHotIndexDAOImpl extends BaseDao implements WorkHotIndexDAO {

	@Override
	public List<WorkHotIndex> queryTopN(int top) {

		return sqlSessionTemplate.selectList(getSqlName("queryTopN"), top);
	}

	@Override
	public List<WorkHotIndex> querySuggestWorksByPage(List<String> sourceTypes, List<Long> tagIds, int fromIndex,
			int limit) {

		Map map = new HashMap();
		
		map.put("typeIds", listTransferToString(sourceTypes));
		map.put("tagIds", listToString(tagIds));
		map.put("offset", fromIndex);
		map.put("limit", limit);

		return sqlSessionTemplate.selectList(getSqlName("querySuggestWorksByPage"), map);
	}
	
}