package com.zhuanquan.app.common.model.author;


/**
 * 
 * @author zhangjun
 *
 */
public class AuthorExtendInfo {
	
	
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	/**
	 * 作者属性
	 * 
	 * @see
	 */
	private Integer attrType;
	
	
	/**
	 * 值
	 */
	private String value;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
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


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	
	
	
	
	
	
}