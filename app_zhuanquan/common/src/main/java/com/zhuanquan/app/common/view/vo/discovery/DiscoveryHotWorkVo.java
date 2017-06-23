package com.zhuanquan.app.common.view.vo.discovery;

import java.io.Serializable;

/**
 * 发现页热门作品
 * @author zhangjun
 *
 */
public class DiscoveryHotWorkVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6399729172254054726L;
	
	
	/**
	 * 作品id
	 * 
	 */
	private long workId;
	
	
	/**
	 * 标题
	 */
	private String subject;
	
	/**
	 * 热度指数
	 */
	private long score;
	
	
	/**
	 * 作者信息
	 */
	private String authorInfo;
	
	



	public long getWorkId() {
		return workId;
	}


	public void setWorkId(long workId) {
		this.workId = workId;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}



	public long getScore() {
		return score;
	}


	public void setScore(long score) {
		this.score = score;
	}


	public String getAuthorInfo() {
		return authorInfo;
	}


	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	
	
	
	
	
	
	
	
	
	
}