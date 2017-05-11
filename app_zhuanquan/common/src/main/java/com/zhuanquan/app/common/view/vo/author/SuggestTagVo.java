package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;

/**
 * 推荐的tag 视图
 * 
 * @author zhangjun
 *
 */
public class SuggestTagVo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3810722274502259506L;

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

}
