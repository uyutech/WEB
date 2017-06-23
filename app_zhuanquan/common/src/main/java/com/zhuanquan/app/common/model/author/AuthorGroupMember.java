package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 组合成员定义
 * 
 * @author zhangjun
 *
 */
public class AuthorGroupMember {

	/**
	 * 组合id，AuthorGroupBase的id
	 */
	private Long groupId;

	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 排序字段
	 */
	private Integer orderNum;

	/**
	 * 状态 0-disable 1-enable
	 */
	private Integer status;
	
	/**
	 * 
	 */
	private Date createTime;
	
	/**
	 * 
	 */
	private Date modifyTime;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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