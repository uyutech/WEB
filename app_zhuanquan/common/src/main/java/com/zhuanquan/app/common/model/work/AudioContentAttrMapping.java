package com.zhuanquan.app.common.model.work;



/**
 * 音频资源映射关系
 * @author zhangjun
 *
 */
public class AudioContentAttrMapping {
	
	/**
	 * 内容 contentid
	 */
	private Long workContentId;

	
	/**
	 * 音频资源的属性类型
	 */
	private Integer attrType;
	
	/**
	 *  属性的值
	 */
	private String value;
	
	
	/**
	 * 排序字段
	 */
	private Integer order;

	
	/**
	 * 状态
	 */
	private Integer status;
	
	


	public Integer getAttrType() {
		return attrType;
	}


	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Long getWorkContentId() {
		return workContentId;
	}


	public void setWorkContentId(Long workContentId) {
		this.workContentId = workContentId;
	}

	
	
}