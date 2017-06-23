package com.zhuanquan.app.common.model.author;



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
	private Long platformId;
	
	

	/**
	 * 主页
	 */
	private String homePage;
	
	
	/**
	 * 粉丝数
	 */
	private Long fansNum;


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


	public Long getPlatformId() {
		return platformId;
	}


	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	
	
	
	
	
}