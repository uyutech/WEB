package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;


/**
 * 作品关联的tag信息
 * @author zhangjun
 *
 */
public class WorkTagBo implements Serializable {
	
	



	/**
	 * 
	 */
	private static final long serialVersionUID = -3446956342642777887L;



	/**
	 * 标签id
	 */
	private long tagId;
	

	
	/**
	 * tag 排序字段
	 */
	private int orderNum;

	/**
	 * sourceid
	 */
	private long sourceId;

	public long getTagId() {
		return tagId;
	}


	public void setTagId(long tagId) {
		this.tagId = tagId;
	}


	public int getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public long getSourceId() {
		return sourceId;
	}


	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	
}