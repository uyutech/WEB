package com.zhuanquan.app.dal.dao.work.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.view.bo.author.AuthorPartnerInfoBo;
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

	@Override
	public List<Long> queryAuthorAttendWorkIds(long authorId) {
		return sqlSessionTemplate.selectList(getSqlName("queryAuthorAttendWorkIds"), authorId);
	}

	@Override
	public List<String> queryAuthorRoleCodesByAuthorId(long authorId) {
		
		return sqlSessionTemplate.selectList(getSqlName("queryAuthorRoleCodesByAuthorId"), authorId);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AuthorPartnerInfoBo> queryAuthorPartnerInfo(long authorId, List<Long> workIds) {
		
		Map map = new HashMap();
		
		map.put("ids", listToString(workIds));
		map.put("authorId", authorId);
		
		
		return sqlSessionTemplate.selectList(getSqlName("queryAuthorPartnerInfo"), map);
	}
	
}