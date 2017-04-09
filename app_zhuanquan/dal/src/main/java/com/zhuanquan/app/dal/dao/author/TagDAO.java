package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.Tag;

public interface TagDAO {
	
	/**
	 * 根据tag id批量查询
	 * @param ids
	 * @return
	 */
	List<Tag> queryTagsByIds(List<Long> tagIds);
	
	/**
	 * 根据tag id查询
	 * @param tagId
	 * @return
	 */
	Tag queryById(long tagId);
	
	
	
	
}