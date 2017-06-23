package com.zhuanquan.app.common.model.work;

/**
 * 作品扩展信息
 * @author zhangjun
 *
 */
public class WorkBaseExtend {
	
	/**
	 * 作品id
	 */
	private Long workId;
	
	/**
	 * 扩展属性类型，
	 * 
	 * @see  com.zhuanquan.app.common.constants.WorkExtendAttrConstants
	 */
	private Integer extendAttr;
	
	
	/**
	 * 关联属性值
	 */
	private String attrVal;
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;
	
	/**
	 * 补充说明
	 */
	private String remark;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Integer getExtendAttr() {
		return extendAttr;
	}

	public void setExtendAttr(Integer extendAttr) {
		this.extendAttr = extendAttr;
	}

	public String getAttrVal() {
		return attrVal;
	}

	public void setAttrVal(String attrVal) {
		this.attrVal = attrVal;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	
}