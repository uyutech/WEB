package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 创作灵感
 * @author zhangjun
 *
 */
public class WorkInspiration {
	
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 作品id
	 */
	private Long workId;
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	
	/**
	 * 灵感
	 */
	private String inspiration;

	/**
	 * 0-disable 1-enable
	 */
	private Integer status;
	
	
	private Date createTime;
	
	
	private Date modifyTime;
	

	public Long getWorkId() {
		return workId;
	}


	public void setWorkId(Long workId) {
		this.workId = workId;
	}


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public String getInspiration() {
		return inspiration;
	}


	public void setInspiration(String inspiration) {
		this.inspiration = inspiration;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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