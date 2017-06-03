package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

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
	 * 作者id str
	 */
	private String authorIdsStr;

//	/**
//	 * 作者id列表
//	 */
//	private List<Long> authorIds;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<Long> getAuthorIds() {
		
		if(StringUtils.isEmpty(authorIdsStr)) {
			return null;
		} else {
			return JSON.parseArray(authorIdsStr, Long.class);
		}
	}

	public String getAuthorIdsStr() {
		return authorIdsStr;
	}

	public void setAuthorIdsStr(String authorIdsStr) {
		this.authorIdsStr = authorIdsStr;
	}

//	public void setAuthorIds(List<Long> authorIds) {
//		this.authorIds = authorIds;
//	}

	
	
}