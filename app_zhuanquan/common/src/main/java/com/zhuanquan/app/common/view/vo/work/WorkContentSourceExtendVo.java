package com.zhuanquan.app.common.view.vo.work;


import java.io.Serializable;


/**
 * 多媒体资源 扩展信息
 * 
 * @author zhangjun
 *
 */
public class WorkContentSourceExtendVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8985704432038636068L;

	/**
	 * 扩展属性定义
	 */
	private Integer extendAttr;

	/**
	 * 属性值
	 */
	private String attrVal;
	
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;

	/**
	 * 补充说明
	 */
	private String remark;

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



}