package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品和tag 的映射表
 * @author zhangjun
 *
 */
public class WorkTagMapping {
	
	/**
	 * 标签id
	 */
	private Long tagId;
	
	/**
	 * 作品id
	 */
	private Long workId;
	

	
	 
	/**
	 * 标签分类
	 */
	private Integer tagType;
	
	
    private Integer status;
    
    
    
    private Date createTime;
    
    
    private Date modifyTime;

	
	
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

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}


	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}
	
	
	
	
	
}