package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhangjun
 *
 */
public class SelectFollowTagsRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741147238734167501L;

	/**
	 * uid
	 */
	private long uid;

	/**
	 * tag id
	 */
	private List<Long> tagIds;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<Long> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<Long> tagIds) {
		this.tagIds = tagIds;
	}

	
}