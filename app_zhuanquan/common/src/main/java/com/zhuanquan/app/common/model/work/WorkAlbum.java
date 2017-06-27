package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品专辑
 * 
 * @author zhangjun
 *
 */
public class WorkAlbum {
	
	
	/**
	 * 专辑id
	 */
	private Long albumId;
	


	/**
	 * 专辑名
	 */
	private String subject;
	
	

	/**
	 * 作品简介
	 */
	private String summary;	
	
	
	/**
	 * 封面图片地址
	 */
	private String covPicUrl;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;


	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;	
	
	
	
	public Long getAlbumId() {
		return albumId;
	}


	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}



	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getCovPicUrl() {
		return covPicUrl;
	}


	public void setCovPicUrl(String covPicUrl) {
		this.covPicUrl = covPicUrl;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
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