package com.zhuanquan.app.common.view.bo;

import java.io.Serializable;

public class BatchUpdateWorkUpvoteNumBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942202732071815942L;
	
	
	/**
	 * 作品id
	 */
	private long workId;
	
	/**
	 * 点赞数
	 */
	private long upvoteNum;

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public long getUpvoteNum() {
		return upvoteNum;
	}

	public void setUpvoteNum(long upvoteNum) {
		this.upvoteNum = upvoteNum;
	}
	
	
	
	
}