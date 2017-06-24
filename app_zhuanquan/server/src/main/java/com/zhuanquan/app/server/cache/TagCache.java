package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.view.bo.TagInfoBo;

/**
 * 
 * @author zhangjun
 *
 */
public interface TagCache {
	
	
	/**
	 * 获取随机的tag
	 * @return
	 */
	List<TagInfoBo> getSuggestTag(long uid,int fromIndex,int limit);
	
	
	/**
	 * 获取tag标签
	 * @param tagIds
	 * @return
	 */
	Map<String,Tag> getTagMapByIds(List<Long> tagIds);
	
	/**
	 * 
	 * @param tagIds
	 * @return
	 */
	List<Tag> getTagListByIds(List<Long> tagIds);
	
	/**
	 * 
	 * @param tagId
	 * @return
	 */
	Tag getTagById(long tagId);
	
	
}