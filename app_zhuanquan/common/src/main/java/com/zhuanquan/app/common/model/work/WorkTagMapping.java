package com.zhuanquan.app.common.model.work;




/**
 * 作品和tag 的映射表
 * @author zhangjun
 *
 */
public class WorkTagMapping {
	
	/**
	 * 标签id
	 */
	private Long tagId;
	
	/**
	 * 作品id
	 */
	private Long workId;
	
	/**
	 * 标签名
	 */
	private String tagName;
	
	 
	/**
	 * 标签分类
	 */
	private Integer tagType;
	
	

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}
	
	
	
	
	
}