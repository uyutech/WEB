package com.zhuanquan.app.common.view.bo.author;

import java.io.Serializable;

/**
 * 作者基本信息
 * @author zhangjun
 *
 */
public class AuthorBaseInfoBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3500979430474349180L;
	

	/**
	 * 作者id
	 */
	private long authorId;

	/**
	 * 头像url
	 */
	private String headUrl;

	/**
	 * 作者名
	 */
	private String authorName;

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	
	
	
	
}