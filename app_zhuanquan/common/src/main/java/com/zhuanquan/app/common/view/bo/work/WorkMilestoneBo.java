package com.zhuanquan.app.common.view.bo.work;

import java.io.Serializable;
import java.util.Date;

import com.zhuanquan.app.common.constants.work.WorkMilestoneAttrConstants;
import com.zhuanquan.app.common.model.work.WorkMilestone;

/**
 * 
 * @author zhangjun
 *
 */
public class WorkMilestoneBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3284581858254391238L;
	
	
	/**
	 * 作品id
	 */
	private long workId;
	
	/**
	 * 属性
	 */
	private int mileAttr;
	
	/**
	 * 属性描述
	 */
	private String mileAttrDesc;
	
	/**
	 * 触发时间
	 */
	private Date mileTime;

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public int getMileAttr() {
		return mileAttr;
	}

	public void setMileAttr(int mileAttr) {
		this.mileAttr = mileAttr;
	}

	public String getMileAttrDesc() {
		return mileAttrDesc;
	}

	public void setMileAttrDesc(String mileAttrDesc) {
		this.mileAttrDesc = mileAttrDesc;
	}

	public Date getMileTime() {
		return mileTime;
	}

	public void setMileTime(Date mileTime) {
		this.mileTime = mileTime;
	}
	
	/**
	 * 
	 * @param workMilestone
	 * @return
	 */
	public static WorkMilestoneBo getBoFromWorkMilestone(WorkMilestone workMilestone)  {
		
		
		WorkMilestoneBo bo = new WorkMilestoneBo();
		
		bo.setMileAttr(workMilestone.getMileAttr());
		bo.setMileAttrDesc(WorkMilestoneAttrConstants.getAttrDesc(workMilestone.getMileAttr()));
		
		bo.setMileTime(workMilestone.getMileTime());
		bo.setWorkId(workMilestone.getWorkId());
		
		return bo;
	}
	
	
}