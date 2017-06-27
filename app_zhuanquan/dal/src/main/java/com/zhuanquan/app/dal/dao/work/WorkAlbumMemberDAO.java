package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkAlbumMember;

/**
 * 
 * @author zhangjun
 *
 */
public interface WorkAlbumMemberDAO {
	
	
	/**
	 * 查询专辑的组成
	 * @param albumId
	 * @return
	 */
	List<WorkAlbumMember> queryMembersByAlbumId(long albumId);
	

	
	
	/**
	 * 查询这些作品涉及到哪些专辑
	 * @param workIds
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Long> queryAlbumIdsByWorkIds(List<Long> workIds,int offset,int limit) ;
	
}