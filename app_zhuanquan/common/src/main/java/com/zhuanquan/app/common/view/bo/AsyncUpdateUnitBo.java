package com.zhuanquan.app.common.view.bo;

import com.zhuanquan.app.common.constants.AsyncUpdateType;

/**
 * 
 * @author zhangjun
 *
 */
public class AsyncUpdateUnitBo {
	
	/**
	 * 
	 */
	private AsyncUpdateType type;
	
	/**
	 * 变化量，可正，可负
	 */
	private int delta;
	
	/**
	 * 目标id，比如作品id，作者id
	 */
	private long targetId;

	public AsyncUpdateType getType() {
		return type;
	}

	public void setType(AsyncUpdateType type) {
		this.type = type;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	
	
	
	
}