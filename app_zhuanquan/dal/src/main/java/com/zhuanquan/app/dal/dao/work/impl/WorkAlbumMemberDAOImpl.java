package com.zhuanquan.app.dal.dao.work.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkAlbumMember;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkAlbumMemberDAO;

@Repository
public class WorkAlbumMemberDAOImpl extends BaseDao implements WorkAlbumMemberDAO {

	@Override
	public List<WorkAlbumMember> queryMembersByAlbumId(long albumId) {
		
		return sqlSessionTemplate.selectList(getSqlName("queryMembersByAlbumId"), albumId);
	}

	@Override
	public List<Long> queryAlbumIdsByWorkIds(List<Long> workIds,int offset,int limit) {
		
		Map map = new HashMap();
		
		map.put("ids", listToString(workIds));
		map.put("offset", offset);
		map.put("limit", limit);
		return sqlSessionTemplate.selectList(getSqlName("queryAlbumIdsByWorkIds"), map);
	}
	
}