package com.zhuanquan.app.common.model.user;

import java.util.Date;

/**
 * 用户关注的作者表
 * @author zhangjun
 *
 */
public class UserFollowAuthor {
	
	
	public static final int STAT_ENABLE = 1;
	
	public static final int STAT_DISABLE = 0;

	
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
	 * 关注程度  0-普通关注  1-特别关注
	 */
	private Integer followLev;

	
	/**
	 * 应援值
	 */
	private Integer supportVal;
	
	 
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
	
	

	public Integer getSupportVal() {
		return supportVal;
	}


	public void setSupportVal(Integer supportVal) {
		this.supportVal = supportVal;
	}


	
	public Integer getFollowLev() {
		return followLev;
	}


	public void setFollowLev(Integer followLev) {
		this.followLev = followLev;
	}


	/**
	 * 创建记录
	 * @param uid
	 * @param authorId
	 * @return
	 */
	public static UserFollowAuthor createFollowRecord(long uid,long authorId) {
		
		UserFollowAuthor record = new UserFollowAuthor();
		
		record.setFollowAuthorId(authorId);
		record.setStatus(STAT_ENABLE);
		record.setUid(uid);
		
		Date now = new Date();
		
		record.setCreateTime(now);
		record.setModifyTime(now);
		
		return record;
	}
	
}