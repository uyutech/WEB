package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 作者所属公司的人员构成
 * 
 * @author zhangjun
 *
 */
public class AuthorCompanyMember {

	/**
	 * 组合id，AuthorCompanyBase的id
	 */
	private Long companyId;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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