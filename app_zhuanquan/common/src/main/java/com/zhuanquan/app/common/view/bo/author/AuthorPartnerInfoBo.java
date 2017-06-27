package com.zhuanquan.app.common.view.bo.author;

import java.io.Serializable;

/**
 * 作者的关系对象
 * @author zhangjun
 *
 */
public class AuthorPartnerInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5576658671663996520L;
	
	
	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	/**
	 * 作者头像
	 */
	private String authorHeader;
	
	/**
	 * 合作次数
	 */
	private int cooperationTimes;

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

	public String getAuthorHeader() {
		return authorHeader;
	}

	public void setAuthorHeader(String authorHeader) {
		this.authorHeader = authorHeader;
	}

	public int getCooperationTimes() {
		return cooperationTimes;
	}

	public void setCooperationTimes(int cooperationTimes) {
		this.cooperationTimes = cooperationTimes;
	}

}