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
	private int platformId;
	
	
	/**
	 * 1-社交类 2-音乐 3-视频 4-图片
	 */
	private Integer type;
	
	/**
	 * 其他平台的logo
	 */
	private String platformLogo;
	
	
	/**
	 * 其他平台的主页
	 */
	private String homePage;
	
	/**
	 * 平台名
	 */
	private String platformName;


	
	
	
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getPlatformName() {
		return platformName;
	}


	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public int getPlatformId() {
		return platformId;
	}


	public void setPlatformId(int platformId) {
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