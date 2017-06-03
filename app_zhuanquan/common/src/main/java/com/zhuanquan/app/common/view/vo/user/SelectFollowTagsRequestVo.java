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
	 * ids 的 tag 的，
	 */
	private String tagIdsStr;
	
//
//	/**
//	 * tag id
//	 */
//	private List<Long> tagIds;
//	

	

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * 
	 * @return
	 */
	public List<Long> getTagIds() {
		
		if(StringUtils.isEmpty(tagIdsStr)) {
			return null;
		} else {
			return JSON.parseArray(tagIdsStr, Long.class);
		}
	}

//	public String getTagIdsStr() {
//		return tagIdsStr;
//	}
//
//	
	public void setTagIdsStr(String tagIdsStr) {
		this.tagIdsStr = tagIdsStr;
	}

	
}