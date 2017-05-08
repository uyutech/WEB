package com.zhuanquan.app.common.model.author;



/**
 *  作者和标签的映射表
 * @author zhangjun
 *
 */
public class AuthorTagMapping {
	
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	/**
	 * 
	 */
	private Long tagId;


	/**
	 * 标签分类:
	 */
	private Integer tagType;

	/**
	 * 状态 0-disable 1-enable
	 */
	private Integer status;

	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public Long getTagId() {
		return tagId;
	}


	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}



	public Integer getTagType() {
		return tagType;
	}


	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}


}