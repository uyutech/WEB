package com.zhuanquan.app.common.model.author;


/**
 * 作者在第三方平台的信息
 * @author zhangjun
 *
 */
public class AuthorThirdPlatformInfo {
	
	/**
	 * 
	 */
	private Long authorId;
	
	
	/**
	 * 第三方平台id   AuthorThirdPlatformDefine.id
	 * 
	 * 
	 */
	private Integer platformId;
	
	

	/**
	 * 主页
	 */
	private String homePage;
	
	
	/**
	 * 粉丝数
	 */
	private Long fansNum;
	
	
	/**
	 * 状态  0-disable 1-enable
	 */
	private Integer status;


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	

	public String getHomePage() {
		return homePage;
	}


	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}


	public Long getFansNum() {
		return fansNum;
	}


	public void setFansNum(Long fansNum) {
		this.fansNum = fansNum;
	}


	public Integer getPlatformId() {
		return platformId;
	}


	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	
}