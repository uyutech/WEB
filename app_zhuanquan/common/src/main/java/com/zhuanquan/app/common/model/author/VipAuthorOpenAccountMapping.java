package com.zhuanquan.app.common.model.author;



/**
 * 大v用户的 各平台账号和我们的作者账号的映射关系
 * @author zhangjun
 *
 */
public class VipAuthorOpenAccountMapping {
	
	
	/**
	 *  关联id，可能是在第三方的uid
	 */
	private String openId;
	
	

	/**
	 * 渠道类型
	 */
	private Integer channelType;
	
	
	/**
	 * 对应的我们平台的 author id
	 */
	private Long authorId;
	
	
	/**
	 * 作者名
	 */
	private String authorName;


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public Integer getChannelType() {
		return channelType;
	}


	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	

	
	
}