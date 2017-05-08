package com.zhuanquan.app.common.model.author;

import java.util.Date;

public class AuthorBase {

	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 作者名
	 */
	private String authorName;

	/**
	 * 作者的标签信息，多个以逗号分割 比如 歌手，作词
	 */
	private String authorTags;

	/**
	 * 作者账号状态
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorTags() {
		return authorTags;
	}

	public void setAuthorTags(String authorTags) {
		this.authorTags = authorTags;
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