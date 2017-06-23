package com.zhuanquan.app.common.view.vo.work;


import java.io.Serializable;


/**
 * 作品关联的tag信息
 * @author zhangjun
 *
 */
public class WorkReferedTagVo implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7145231006517595461L;


	/**
	 * 标签id
	 */
	private long tagId;
	

	
	/**
	 * tag 排序字段
	 */
	private int orderNum;

	

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
	
	
}