package com.zhuanquan.app.common.model.common;


/**
 *  标签表
 * @author zhangjun
 *
 */
public class Tag {
	
	/**
	 * 标签id
	 */
	private Long tagId;
	
	/**
	 * 标签名
	 */
	private String tagName;
	
	/**
	 * 标签引用次数
	 */
	private Long citedNum;
	
	/**
	 * tag 类型
	 */
	private Integer tagType;
	
	/**
	 * 父节点标签
	 */
	private Long fatherTagId;
	

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getCitedNum() {
		return citedNum;
	}

	public void setCitedNum(Long citedNum) {
		this.citedNum = citedNum;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Long getFatherTagId() {
		return fatherTagId;
	}

	public void setFatherTagId(Long fatherTagId) {
		this.fatherTagId = fatherTagId;
	}
	

	
}