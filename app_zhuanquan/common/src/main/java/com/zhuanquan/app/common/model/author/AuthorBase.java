package com.zhuanquan.app.common.model.author;





public class AuthorBase {
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	
	/**
	 * 作者的标签信息，多个以逗号分割   比如   歌手，作词
	 */
	private String authorTags;


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
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



	
	
}