package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 具体的资源分类
 * @author zhangjun
 *
 */
public class WorkSourceTypeDefine {
	

	/**
	 * 举例，假设 视频100，视频的子类就是100 xxx 这种，视频子类的子类就是 100 xxx xxx
	 */
	private String sourceType;
	
	
	/**
	 * 当前的资源的分类级别：比如视频属于第一类，视频的子类属于第二类
	 */
	private Integer lv;
	
	/**
	 * 类型名
	 */
	private String typeName;
	
	
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

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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