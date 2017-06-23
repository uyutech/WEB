package com.zhuanquan.app.common.model.work;

import com.zhuanquan.app.common.view.vo.work.WorkContentSourceExtendVo;

/**
 * 作品资源扩展信息表
 * 
 * @author zhangjun
 *
 */
public class WorkContentSourceExtend {

	/**
	 * source id
	 */
	private Long sourceId;

	/**
	 * 扩展属性
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

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
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

	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public static WorkContentSourceExtend createRecord(long sourceId,WorkContentSourceExtendVo vo){
		
		WorkContentSourceExtend record = new WorkContentSourceExtend();
		
		record.setAttrVal(vo.getAttrVal());
		record.setExtendAttr(vo.getExtendAttr());
		record.setOrderNum(vo.getOrderNum());
		record.setSourceId(sourceId);
		record.setRemark(vo.getRemark());
		
		return record;
		
	}
}