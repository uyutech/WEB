package com.zhuanquan.app.common.view.vo.sync;

import java.io.Serializable;

/**
 * 多媒体资源关联的作者
 * 
 * @author zhangjun
 *
 */
public class MediaSourceReleatedAuthorVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1013049320551809957L;

	/**
	 * 作者id
	 */
	private long authorId;

	/**
	 * 角色类型:
	 */
	private int roleType;

	/**
	 * 创作灵感
	 */
	private String inspiration;
	
	/**
	 * 排序字段
	 */
	private int orderNum;

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public String getInspiration() {
		return inspiration;
	}

	public void setInspiration(String inspiration) {
		this.inspiration = inspiration;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

}