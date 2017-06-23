package com.zhuanquan.app.common.view.vo.sync;

import java.util.List;

/**
 * 
 * 
 * 
 * @author zhangjun
 *
 */
public class ImportAuthorInfoVo {

	/**
	 * 作者名
	 */
	private String authorName;

	/**
	 * 作者头像
	 */
	private String headUrl;

    /**
     * 其他平台信息，主页，粉丝数等
     */
	private List<AuthorOtherPlatformInfo> otherPlatformInfo;
	
	
	
	
	
	
	
	
	
	
	/**
	 * 在其他平台的信息
	 * 
	 * @author zhangjun
	 *
	 */
	public class AuthorOtherPlatformInfo {

		/**
		 * 平台类型  0-社交类 1-音乐 2-视频 3-图片
		 */
		private int platformType;

		/**
		 * 平台名
		 */
		private String platformName;

		/**
		 * 平台主页地址
		 */
		private String homePage;

		/**
		 * 平台粉丝数
		 */
		private long fansNum;

		public int getPlatformType() {
			return platformType;
		}

		public void setPlatformType(int platformType) {
			this.platformType = platformType;
		}

		public String getPlatformName() {
			return platformName;
		}

		public void setPlatformName(String platformName) {
			this.platformName = platformName;
		}

		public String getHomePage() {
			return homePage;
		}

		public void setHomePage(String homePage) {
			this.homePage = homePage;
		}

		public long getFansNum() {
			return fansNum;
		}

		public void setFansNum(long fansNum) {
			this.fansNum = fansNum;
		}
		
		
		
		

	}

}