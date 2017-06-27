package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorPlatformInfoBo;

/**
 * 作者主页信息视图
 * 
 * @author zhangjun
 *
 */
public class AuthorHomeInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2746273632785467158L;
	
	

	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 是否已关注
	 */
	private boolean hasFollowed;
	
	
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
	 * 职业角色类型，按照第一级类别划分.比如 策，文，歌，舞这种
	 * 返回的是  100,101这样的rolecode的前三位
	 */
	private List<String> roleTypes;
	
	
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
	
	/**
	 * 作者的作品信息
	 */
	private AuthorWorksPageQueryVo worksView;
	
	

	/**
	 * 作者的关系页面
	 */
	private AuthorRelationshipPageQueryVo relationView;
	
	
	/**
	 * 作者的专辑查询页面
	 */
	private AuthorAlbumPageQueryVo albumView;
	

	public AuthorWorksPageQueryVo getWorksView() {
		return worksView;
	}


	public void setWorksView(AuthorWorksPageQueryVo worksView) {
		this.worksView = worksView;
	}


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




	public List<String> getRoleTypes() {
		return roleTypes;
	}


	public void setRoleTypes(List<String> roleTypes) {
		this.roleTypes = roleTypes;
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


	public AuthorRelationshipPageQueryVo getRelationView() {
		return relationView;
	}


	public void setRelationView(AuthorRelationshipPageQueryVo relationView) {
		this.relationView = relationView;
	}


	public AuthorAlbumPageQueryVo getAlbumView() {
		return albumView;
	}


	public void setAlbumView(AuthorAlbumPageQueryVo albumView) {
		this.albumView = albumView;
	}


	public boolean isHasFollowed() {
		return hasFollowed;
	}


	public void setHasFollowed(boolean hasFollowed) {
		this.hasFollowed = hasFollowed;
	}
	
	

	
	
}