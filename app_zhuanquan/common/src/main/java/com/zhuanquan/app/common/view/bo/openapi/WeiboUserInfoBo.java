package com.zhuanquan.app.common.view.bo.openapi;


import java.io.Serializable;



/**
 * 第三方用户信息
 * @author zhangjun
 *
 */
public class WeiboUserInfoBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 495973957363619551L;
	
	
	/**
	 * 用户昵称
	 */
	private String screen_name;
	
	/**
	 * 友好显示名称
     *
	 */
	private String name;
	
	/**
	 * 用户所在省级ID
	 */
	private int province;
	
	/**
	 * 用户所在城市ID
	 */
	private int city;
	
	/**
	 * 用户所在地
	 */
	private String location;

	/**
	 * 用户个人描述
	 */
	private String description;
	
	/**
	 * 用户博客地址
	 */
	private String url;
	
	/**
	 * 用户头像地址（中图），50×50像素
	 */
	private String profile_url;
	
	
	
	private String profile_image_url;
	
	/**
	 * 用户的个性化域名
	 */
	private String domain;
	
	/**
	 * 用户的微号
	 */
	private String weihao;

    /**
     * 性别，m：男、f：女、n：未知
     */
	private String gender;

	/**
	 * 	粉丝数
	 */
	private int followers_count;
	
	/**
	 * 关注数
	 */
	private int friends_count;
	
	/**
	 * 微博数
	 */
	private int statuses_count;
	
	/**
	 * 收藏数
	 */
	private int favourites_count;

    /**
     * 用户创建（注册）时间,Tue May 09 22:12:16 +0800 2017
     */
	private String created_at;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWeihao() {
		return weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	
	
	
	
}