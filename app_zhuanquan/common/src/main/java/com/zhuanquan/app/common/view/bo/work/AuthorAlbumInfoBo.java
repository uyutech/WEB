package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;

/**
 * 作者的 专辑基础信息
 * @author zhangjun
 *
 */
public class AuthorAlbumInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2117501170496108050L;
	
	
	/**
	 * 专辑id
	 */
	private long albumId;
	
	/**
	 * 专辑名字
	 */
	private String albumName;
	
	/**
	 * 专辑热度
	 */
	private long hotScore;

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public long getHotScore() {
		return hotScore;
	}

	public void setHotScore(long hotScore) {
		this.hotScore = hotScore;
	}
	

	
}