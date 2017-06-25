package com.zhuanquan.app.common.model.common;

import java.util.Date;

/**
 * 管理发现页面里的 sourcetype 推荐
 * 
 * 
 * @author zhangjun
 *
 */
public class SuggestSourceMgr {
	
	/**
	 * 批次号
	 */
	private long batchNum;
	
	/**
	 * 资源类型的code
	 * 
	 * @see com.zhuanquan.app.common.model.work.WorkSourceTypeDefine
	 */
	private String sourceType;
	
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

	public long getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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