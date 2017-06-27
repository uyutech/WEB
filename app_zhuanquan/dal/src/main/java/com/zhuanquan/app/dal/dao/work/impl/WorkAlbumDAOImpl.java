package com.zhuanquan.app.dal.dao.work.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkAlbum;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkAlbumDAO;

@Repository
public class WorkAlbumDAOImpl extends BaseDao implements WorkAlbumDAO {

	@Override
	public List<WorkAlbum> queryByAlbumIds(List<Long> albumIds) {
		
		Map map = new HashMap();
		map.put("ids", listToString(albumIds));
		
		
		return sqlSessionTemplate.selectList(getSqlName("queryByAlbumIds"), map);
	}

	@Override
	public WorkAlbum queryById(long albumId) {
		return sqlSessionTemplate.selectOne(getSqlName("queryById"), albumId);
	}
	
}