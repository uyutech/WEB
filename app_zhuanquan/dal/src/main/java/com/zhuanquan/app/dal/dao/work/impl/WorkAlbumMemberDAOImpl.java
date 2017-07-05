package com.zhuanquan.app.dal.dao.work.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Long> queryAlbumIdsByWorkIds(List<Long> workIds,int offset,int limit) {
		
		Map map = new HashMap();
		
		map.put("ids", listToString(workIds));
		map.put("offset", offset);
		map.put("limit", limit);
		
		return sqlSessionTemplate.selectList(getSqlName("queryAlbumIdsByWorkIds"), map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Pair<Long, Long>> querySuggestAlbumsByPage(List<String> sourceTypes, List<Long> tagIds, int fromIndex,
			int limit) {
		
		Map map = new HashMap();
		
		map.put("typeIds", listTransferToString(sourceTypes));
		map.put("tagIds", listToString(tagIds));
		map.put("offset", fromIndex);
		map.put("limit", limit);
		
		List<Map<String,String>> result = this.sqlSessionTemplate.selectList(this.getSqlName("querySuggestAlbumsByPage"), map);
		
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		
		List<Pair<Long, Long>> list = new ArrayList<Pair<Long, Long>>();
		for(Map<String,String> parm:result) {
			
			long albumId = Long.parseLong(parm.get("album_Id"));
			long totalScore = Long.parseLong(parm.get("total"));
			list.add(Pair.of(albumId, totalScore));
		}

		return list;
	}
	
}