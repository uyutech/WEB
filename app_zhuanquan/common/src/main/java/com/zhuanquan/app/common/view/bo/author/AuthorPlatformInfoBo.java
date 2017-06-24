package com.zhuanquan.app.common.view.bo.author;



import java.io.Serializable;

/**
 * 作者在其他平台的信息
 * @author zhangjun
 *
 */
public class AuthorPlatformInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1602466439800241327L;
	
	/**
	 * 其他平台的id
	 */
	private long platformId;
	
	
	/**
	 * 其他平台的logo
	 */
	private String platformLogo;
	
	
	/**
	 * 其他平台的主页
	 */
	private String homePage;


	public long getPlatformId() {
		return platformId;
	}


	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}


	public String getPlatformLogo() {
		return platformLogo;
	}


	public void setPlatformLogo(String platformLogo) {
		this.platformLogo = platformLogo;
	}


	public String getHomePage() {
		return homePage;
	}


	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	
	
	
	
}