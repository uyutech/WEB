package com.zhuanquan.app.common.view.vo.author;


import java.io.Serializable;

public class SuggestAuthorUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 670077217836892670L;
	
	
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
	 * 默认就是关注的，根据第三方登录同步过来的关注列表，如果作者也在我们平台，默认就是关注的
	 */
	private int isDefaultFollow;

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

	public int getIsDefaultFollow() {
		return isDefaultFollow;
	}

	public void setIsDefaultFollow(int isDefaultFollow) {
		this.isDefaultFollow = isDefaultFollow;
	}
	

}