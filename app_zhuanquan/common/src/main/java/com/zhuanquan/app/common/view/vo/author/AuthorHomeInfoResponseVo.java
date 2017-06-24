package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorPlatformInfoBo;


public class AuthorHomeInfoResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2746273632785467158L;
	
	

	/**
	 * 作者id
	 */
	private long authorId;
	
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	
	/**
	 * 别名
	 */
	private String nickName;
	
	/**
	 * 头像地址
	 */
	private String headUrl;
	
	
	/**
	 * 职业角色
	 */
	private List<String> roleStrs;
	
	
	/**
	 * 粉丝数
	 */
	private long fansNum;
	
	
	/**
	 * 热度
	 */
	private long hotScore;
	
	/**
	 * 是否已关注  0-未关注 1-已关注
	 */
	private int isFollowed;

	/**
	 * 第三方平台的信息
	 */
	private List<AuthorPlatformInfoBo> thirdPlatList;
	
	

	public List<AuthorPlatformInfoBo> getThirdPlatList() {
		return thirdPlatList;
	}


	public void setThirdPlatList(List<AuthorPlatformInfoBo> thirdPlatList) {
		this.thirdPlatList = thirdPlatList;
	}


	public int getIsFollowed() {
		return isFollowed;
	}


	public void setIsFollowed(int isFollowed) {
		this.isFollowed = isFollowed;
	}


	public long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getHeadUrl() {
		return headUrl;
	}


	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}


	public List<String> getRoleStrs() {
		return roleStrs;
	}


	public void setRoleStrs(List<String> roleStrs) {
		this.roleStrs = roleStrs;
	}


	public long getFansNum() {
		return fansNum;
	}


	public void setFansNum(long fansNum) {
		this.fansNum = fansNum;
	}


	public long getHotScore() {
		return hotScore;
	}


	public void setHotScore(long hotScore) {
		this.hotScore = hotScore;
	}
	
	

	
	
}