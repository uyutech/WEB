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
	 * 担当的角色类型
	 */
	private Integer roleType;

	/**
	 * 描述信息： 比如 XX于2017.5.1参与了xx作品的创作，担任了xx角色之类的
	 */
	private String desc;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 状态 0-删除 1-正常
	 */
	private Integer status;

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

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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