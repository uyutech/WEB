package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 作者和标签的映射表
 * 
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
	 * 排序字段
	 */
	private Integer orderNum;

	/**
	 * 状态 0-disable 1-enable
	 */
	private Integer status;
	
	

	private Date createTime;

	private Date modifyTime;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	

}