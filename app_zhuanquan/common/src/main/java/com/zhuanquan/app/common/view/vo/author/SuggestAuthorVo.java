package com.zhuanquan.app.common.view.vo.author;

/**
 * 推荐作者信息
 * 
 * @author zhangjun
 *
 */
public class SuggestAuthorVo {

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