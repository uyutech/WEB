package com.zhuanquan.app.dal.dao.work.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

	@Override
	public List<Long> queryTagIds(List<String> sourceTypes, int fromIndex, int limit) {
		
		Map map = new HashMap();
		
		map.put("ids", listTransferToString(sourceTypes));
		map.put("offset", fromIndex);
		map.put("limit", limit);
		
		
		List<Map<String,Object>> list = sqlSessionTemplate.selectList(getSqlName("queryTagIds"), map);

		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		List<Long> result = new ArrayList<Long>();
		
		for(Map<String,Object> temp:list) {
			
			Object tid = temp.get("tag_id");
			result.add(Long.parseLong(tid.toString()));
		}
		
		return result;

	}
	
}