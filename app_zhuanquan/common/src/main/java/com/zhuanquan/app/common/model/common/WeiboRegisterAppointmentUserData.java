package com.zhuanquan.app.common.model.common;

import com.zhuanquan.app.common.view.bo.openapi.WeiboUserInfoBo;

/**
 * 微博注册的用户数据
 * @author zhangjun
 *
 */
public class WeiboRegisterAppointmentUserData {
	

	private String openId;
	
	
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
	private Integer province;
	
	/**
	 * 用户所在城市ID
	 */
	private Integer city;
	
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
	
	/**
	 * 
	 */
	private String profile_image_url;
	
	
	
	/**
	 * 用户的个性化域名
	 */
	private String domain;
	
	/**
	 * 用户的微号
	 */
	private String wei_hao;

    /**
     * 性别，m：男、f：女、n：未知
     */
	private String gender;

	/**
	 * 	粉丝数
	 */
	private Integer followers_count;
	
	/**
	 * 关注数
	 */
	private Integer friends_count;
	
	/**
	 * 微博数
	 */
	private Integer statuses_count;
	
	/**
	 * 收藏数
	 */
	private Integer favourites_count;

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

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
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



	public String getWei_hao() {
		return wei_hao;
	}

	public void setWei_hao(String wei_hao) {
		this.wei_hao = wei_hao;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(Integer followers_count) {
		this.followers_count = followers_count;
	}

	public Integer getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(Integer friends_count) {
		this.friends_count = friends_count;
	}

	public Integer getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(Integer statuses_count) {
		this.statuses_count = statuses_count;
	}

	public Integer getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(Integer favourites_count) {
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 
	 * @param bo
	 * @return
	 */
	public static WeiboRegisterAppointmentUserData createRecord(String openId,WeiboUserInfoBo bo){
		
		WeiboRegisterAppointmentUserData record = new WeiboRegisterAppointmentUserData();
		
		record.setOpenId(openId);
		
		record.setCity(bo.getCity());
		record.setCreated_at(bo.getCreated_at());
		record.setDescription(bo.getDescription());
		record.setDomain(bo.getDomain());
		record.setFavourites_count(bo.getFavourites_count());
		record.setFollowers_count(bo.getFollowers_count());
		record.setFriends_count(bo.getFriends_count());
		record.setGender(bo.getGender());
		record.setLocation(bo.getLocation());
		record.setName(bo.getName());
		record.setProfile_url(bo.getProfile_url());
		record.setProvince(bo.getProvince());
		record.setScreen_name(bo.getScreen_name());
		record.setStatuses_count(bo.getStatuses_count());
		record.setUrl(bo.getUrl());
		record.setWei_hao(bo.getWeihao());
		record.setProfile_image_url(bo.getProfile_image_url());
		
		return record;
		
	}

}