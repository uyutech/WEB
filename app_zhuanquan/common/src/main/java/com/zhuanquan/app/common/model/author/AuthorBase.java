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
	 * 头像地址
	 */
	private String headUrl;

	/**
	 * 作者账号状态
	 */
	private Integer status;
	
	/**
	 * 粉丝数
	 */
	private long fansNum;
	

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

//	public String getAuthorTags() {
//		return authorTags;
//	}
//
//	public void setAuthorTags(String authorTags) {
//		this.authorTags = authorTags;
//	}

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

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public long getFansNum() {
		return fansNum;
	}

	public void setFansNum(long fansNum) {
		this.fansNum = fansNum;
	}

	
	
}