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
	
	/**
	 * tag 描述,比如 翻唱,作家，摇滚歌手
	 */
	private String tagDesc;
	
	/**
	 * tag ids ,比如   110,111,222
	 */
	private String tagIds;
	

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

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	
	
}