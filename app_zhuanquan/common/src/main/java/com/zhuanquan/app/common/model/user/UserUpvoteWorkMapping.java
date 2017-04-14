package com.zhuanquan.app.common.model.user;



/**
 * 用户对作品的点赞记录表
 * 
 * @author zhangjun
 *
 */
public class UserUpvoteWorkMapping {
	
	/**
	 * enbale状态，即点赞
	 */
	public static final int STAT_ENABLE = 1;
	
	/**
	 * diable状态，即取消点赞
	 */
	public static final int STAT_DISABLE = 0;

	
	/**
	 * uid
	 */
	private Long uid;
	
	/**
	 * 被点赞的作品id
	 */
	private Long workId;
	
	/**
	 * 点赞状态
	 */
	private Integer status;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	} 
	
	
	
	
}