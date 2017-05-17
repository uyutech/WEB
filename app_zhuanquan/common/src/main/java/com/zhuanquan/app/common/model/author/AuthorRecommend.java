package com.zhuanquan.app.common.model.author;



/**
 * 作者推荐度表
 * @author zhangjun
 *
 */
public class AuthorRecommend {
	
	
	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	
	/**
	 * 推荐度
	 */
	private long hotDegree;


	public long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public long getHotDegree() {
		return hotDegree;
	}


	public void setHotDegree(long hotDegree) {
		this.hotDegree = hotDegree;
	}



}