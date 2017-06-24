package com.zhuanquan.app.common.view.vo.discovery;


import java.io.Serializable;


public class DiscoveryHotWorkAlbumVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708604567834503659L;
	
	
	
	
	/**
	 * 专辑id
	 * 
	 */
	private long albumId;
	
	
	/**
	 * 专辑标题
	 */
	private String subject;
	
	/**
	 * 热度指数
	 */
	private long hotIndex;
	
	
	/**
	 * 作者信息
	 */
	private String authorInfo;


	public long getAlbumId() {
		return albumId;
	}


	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public long getHotIndex() {
		return hotIndex;
	}


	public void setHotIndex(long hotIndex) {
		this.hotIndex = hotIndex;
	}


	public String getAuthorInfo() {
		return authorInfo;
	}


	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	
	
	
	
	
	
	
}