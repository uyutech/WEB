package com.zhuanquan.app.common.model.user;

import java.util.Date;

/**
 * 用户收藏作品信息
 * 
 * @author zhangjun
 *
 */
public class UserFavourite {
	
	
	/**
	 * 冗余 useri的内容
	 */
	private Long userId;
	
	
	/**
	 * 收藏分组id
	 */
	private Long favGroupId;
	
	/**
	 * 收藏的作品id
	 */
	private Long workId;

	
	/**
	 * 收藏/取消收藏
	 */
	private  Integer status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFavGroupId() {
		return favGroupId;
	}

	public void setFavGroupId(Long favGroupId) {
		this.favGroupId = favGroupId;
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