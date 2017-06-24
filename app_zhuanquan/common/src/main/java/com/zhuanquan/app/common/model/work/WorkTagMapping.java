package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品和tag 的映射表
 * @author zhangjun
 *
 */
public class WorkTagMapping {
	
	/**
	 * 可用
	 */
	public static final int STAT_ENABLE =1;
	
	/**
	 * 不可用
	 */
	public static final int STAT_DISABLE =0;

	
	/**
	 * 作品id
	 */
	private Long workId;
	
	/**
	 * 标签id
	 */
	private Long tagId;

	

	/**
	 * 标签分类，tag表定义，这里只包含风格和ip类的标签。冗余字段
	 */
	private Integer tagType;
	
	
	/**
	 * 0-disable 1-enable
	 */
    private Integer status;
    
    
    /**
     * 标签对应哪个多媒体资源source，如果是作品本身的标签，sourceid为0
     */
    private Long sourceId;
    
    /**
     * 排序字段
     */
    private Integer orderNum;
    
    
    private Date createTime;
    
    
    private Date modifyTime;

	
	
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
	
	
	
	
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public static WorkTagMapping createRecord(long workId,long tagId,int tagType,int orderNum,long sourceId){
		
		WorkTagMapping record = new WorkTagMapping();
		
		record.setTagId(tagId);
		record.setStatus(STAT_ENABLE);
		record.setTagType(tagType);
		record.setWorkId(workId);
		record.setOrderNum(orderNum);
		
		record.setSourceId(sourceId);
		
		Date now = new Date();
		record.setCreateTime(now);
		record.setModifyTime(now);
		
		return record;
	}
	
	
}