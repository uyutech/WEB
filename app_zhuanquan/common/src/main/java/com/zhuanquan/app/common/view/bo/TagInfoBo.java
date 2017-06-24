package com.zhuanquan.app.common.view.bo;

import java.io.Serializable;

/**
 * 推荐的tag 视图
 * 
 * @author zhangjun
 *
 */
public class TagInfoBo implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6640174804798524630L;

	/**
	 * tag id
	 */
	private long tagId;

	/**
	 * tag 类型
	 */
	private int tagType;

	/**
	 * tag 名
	 */
	private String tagName;
	
//	/**
//	 * 第三方同步过来的，默认关注  0-不关注 1-关注
//	 */
//	boolean isDefaultFollowed;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public int getTagType() {
		return tagType;
	}

	public void setTagType(int tagType) {
		this.tagType = tagType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

//	public boolean isDefaultFollowed() {
//		return isDefaultFollowed;
//	}
//
//	public void setDefaultFollowed(boolean isDefaultFollowed) {
//		this.isDefaultFollowed = isDefaultFollowed;
//	}
//	
//	
	

}
