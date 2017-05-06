


package com.zhuanquan.app.common.model.work;



/**
 * 视频资源映射关系
 * @author zhangjun
 *
 */
public class VedioContentAttrMapping {
	
	/**
	 * 内容 contentid
	 */
	private Long workContentId;
	
	/**
	 * 视频资源的属性类型
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



	public Long getWorkContentId() {
		return workContentId;
	}


	public void setWorkContentId(Long workContentId) {
		this.workContentId = workContentId;
	}


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
	
	
	
	
}