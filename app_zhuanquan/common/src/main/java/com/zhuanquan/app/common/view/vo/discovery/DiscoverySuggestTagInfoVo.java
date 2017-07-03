package com.zhuanquan.app.common.view.vo.discovery;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.TagInfoBo;


/**
 * 发现页 ，推荐的可选的tag信息
 * @author zhangjun
 *
 */
public class DiscoverySuggestTagInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7044254834942348192L;
	
	
	/**
	 * 推荐的tag信息
	 */
	private List<TagInfoBo> tagList;

	/**
	 * 从哪个开始
	 */
	private int fromIndex;
	
	/**
	 * 限制
	 */
	private int limit;


	public List<TagInfoBo> getTagList() {
		return tagList;
	}


	public void setTagList(List<TagInfoBo> tagList) {
		this.tagList = tagList;
	}


	public int getFromIndex() {
		return fromIndex;
	}


	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	
	
	
}