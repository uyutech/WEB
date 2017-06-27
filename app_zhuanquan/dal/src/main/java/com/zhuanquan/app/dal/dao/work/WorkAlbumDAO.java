package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkAlbum;

public interface WorkAlbumDAO {
	
	
	/**
	 * 批量查询
	 * @param albumIds
	 * @return
	 */
	List<WorkAlbum> queryByAlbumIds(List<Long> albumIds);
	
	/**
	 * 查询单个
	 * @param albumId
	 * @return
	 */
	WorkAlbum queryById(long albumId);
	
}