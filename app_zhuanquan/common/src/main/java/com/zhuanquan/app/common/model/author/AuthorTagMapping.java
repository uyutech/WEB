package com.zhuanquan.app.common.model.author;



/**
 *  作者和标签的映射表
 * @author zhangjun
 *
 */
public class AuthorTagMapping {
	
	
	private Long tagId;
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	
	/**
	 * 标签名
	 */
	private String tagName;
	
	
	/**
	 * 标签分类
	 */
	private Integer tagType;


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