package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品的创作里程碑 记录
 * @author zhangjun
 *
 */
public class WorkMilestone {
	
	
	/**
	 * 
	 */
	private Long workId;
	

	/**
	 * 里程碑属性  ，比如 开坑时间- 发布时间 -预告时间
	 * 
	 * @see com.zhuanquan.app.common.constants.work.WorkMilestoneAttrConstants
	 */
	private Integer mileAttr;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;
	

	/**
	 * 里程碑时间
	 */
	private Date mileTime;
	

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Integer getMileAttr() {
		return mileAttr;
	}

	public void setMileAttr(Integer mileAttr) {
		this.mileAttr = mileAttr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getMileTime() {
		return mileTime;
	}

	public void setMileTime(Date mileTime) {
		this.mileTime = mileTime;
	}


}