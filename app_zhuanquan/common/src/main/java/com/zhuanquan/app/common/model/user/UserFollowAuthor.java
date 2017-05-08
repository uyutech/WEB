package com.zhuanquan.app.common.model.user;

import java.util.Date;

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

	
	 
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

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