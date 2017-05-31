package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 作者的热度指数表
 * 
 * @author zhangjun
 *
 */
public class AuthorHotIndexes {

	// 作者id
	private Long authorId;


	/**
	 * 热度指数
	 */
	private Long hotIndex;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getHotIndex() {
		return hotIndex;
	}

	public void setHotIndex(Long hotIndex) {
		this.hotIndex = hotIndex;
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