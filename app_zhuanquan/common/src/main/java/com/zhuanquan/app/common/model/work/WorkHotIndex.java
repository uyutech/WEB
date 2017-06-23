package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品的热度指数表
 * 
 * @author zhangjun
 *
 */
public class WorkHotIndex {

	// 作品id
	private Long workId;


	/**
	 * 热度指数
	 */
	private Long score;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;



	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}



	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


}