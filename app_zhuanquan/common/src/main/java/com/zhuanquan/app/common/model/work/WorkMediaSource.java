package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品内容详情
 * 
 * @author zhangjun
 *
 */
public class WorkMediaSource {
	
	/**
	 * 内容详情id
	 */
	private Long source_id;
	
	/**
	 * 作品id
	 */
	private Long workId;
	
	/**
	 * 多媒体资源渠道  站内资源/5sing/等等
	 */
	private Integer sourceChannel;
	
	/**
	 * 大类区分：0-音频  1-视频 2-图片 
	 */
	private Integer category;
	
	/**
	 * 具体资源类型  海报/原画/美工    MAD/MMD等等
	 */
	private Integer sourceType;

	
	
	/**
	 * 音频视频图片的url
	 */
	private String sourceVal;
	
	
	/**
	 * 状态 0-废弃   1-启用
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


	public Long getSource_id() {
		return source_id;
	}


	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}






	public Integer getCategory() {
		return category;
	}


	public void setCategory(Integer category) {
		this.category = category;
	}


	public Integer getSourceType() {
		return sourceType;
	}


	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}


	public String getSourceVal() {
		return sourceVal;
	}


	public void setSourceVal(String sourceVal) {
		this.sourceVal = sourceVal;
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


	public Integer getSourceChannel() {
		return sourceChannel;
	}


	public void setSourceChannel(Integer sourceChannel) {
		this.sourceChannel = sourceChannel;
	}


	public Long getWorkId() {
		return workId;
	}


	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	
	
	
	
}