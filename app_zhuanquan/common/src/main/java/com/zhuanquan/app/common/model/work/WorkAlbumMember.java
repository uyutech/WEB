package com.zhuanquan.app.common.model.work;

/**
 * 专辑组成
 * @author zhangjun
 *
 */
public class WorkAlbumMember {
	
	/**
	 * 专辑id
	 */
	private Long albumId;
	
	
	/**
	 * 作品id
	 */
	private Long workId;
	
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;


	public Long getAlbumId() {
		return albumId;
	}


	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}


	public Long getWorkId() {
		return workId;
	}


	public void setWorkId(Long workId) {
		this.workId = workId;
	}


	public Integer getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	
}