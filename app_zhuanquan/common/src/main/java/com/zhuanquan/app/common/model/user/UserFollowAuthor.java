package com.zhuanquan.app.common.model.user;

/**
 * 用户关注的作者表
 * @author zhangjun
 *
 */
public class UserFollowAuthor {
	
	
	/**
	 *  用户id
	 */
	private Long uid;
	
	/**
	 * 作者id表
	 */
	private Long followAuthorId;
	
	
	/**
	 * enable  disable等
	 */
	private Integer status;


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public Long getFollowAuthorId() {
		return followAuthorId;
	}


	public void setFollowAuthorId(Long followAuthorId) {
		this.followAuthorId = followAuthorId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}