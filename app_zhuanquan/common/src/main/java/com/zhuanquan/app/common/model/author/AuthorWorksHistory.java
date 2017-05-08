package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 作者参与过的作品
 * 
 * @author zhangjun
 *
 */
public class AuthorWorksHistory {

	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 参与的作品id
	 */
	private Long workId;

	/**
	 * 担当的角色id
	 */
	private Integer roleId;

	
	
	/**
	 * 关联记录id
	 */
	private Long referId;
	
	
	/**
	 * 指明关联id 是来自于哪的
	 */
	private Integer referType;
	
	

	/**
	 * 描述信息
	 */
	private String summary;
	
	

	/**
	 * 状态 0-删除 1-正常
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;



	public Long getReferId() {
		return referId;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public Integer getReferType() {
		return referType;
	}

	public void setReferType(Integer referType) {
		this.referType = referType;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}



	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}



	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}