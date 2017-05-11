package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhangjun
 *
 */
public class SelectFollowAuthorRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741147238734167501L;

	/**
	 * uid
	 */
	private long uid;

	/**
	 * 作者id列表
	 */
	private List<Long> authorIds;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<Long> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(List<Long> authorIds) {
		this.authorIds = authorIds;
	}

}