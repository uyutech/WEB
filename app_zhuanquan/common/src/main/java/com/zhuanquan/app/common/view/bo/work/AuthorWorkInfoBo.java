package com.zhuanquan.app.common.view.bo.work;



import java.io.Serializable;


/**
 * 作者的 作品基础信息
 * @author zhangjun
 *
 */
public class AuthorWorkInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2398959939378600539L;
	
	
	/**
	 * 作品id
	 */
	private long workId;
	
	
	/**
	 * 作品标题
	 */
	private String subject;
	
	/**
	 * 热度
	 */
	private long hotScore;

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

	public long getHotScore() {
		return hotScore;
	}

	public void setHotScore(long hotScore) {
		this.hotScore = hotScore;
	}
	
	
	
	
	
}