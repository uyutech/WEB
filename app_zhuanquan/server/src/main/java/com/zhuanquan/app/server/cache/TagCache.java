package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;

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
	List<SuggestTagVo> getSuggestTag(int pageNum,int pageSize);
	
	
	/**
	 * 获取tag标签
	 * @param tagIds
	 * @return
	 */
	Map<String,Tag> getTagListByIds(List<Long> tagIds);
	
	
}