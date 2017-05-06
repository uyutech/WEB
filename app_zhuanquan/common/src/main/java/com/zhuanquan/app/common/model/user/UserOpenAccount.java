package com.zhuanquan.app.common.model.user;

import java.io.Serializable;
import java.util.Date;

import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.utils.MD5;

public class UserOpenAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711050830921914903L;
	
	/**
	 * 未激活状态
	 */
	public static final int STATS_UNACTIVE = 0;
	
	/**
	 * 已激活状态
	 */
	public static final int STATS_ACTIVE = 1;
	
	
	
	/**
	 * 大v的账户
	 */
	public static final int VIP_ACCOUNT = 1;
	
	/**
	 * 普通账户
	 */
	public static final int NORMAL_ACCOUNT = 0;

	

	

	/**
	 *  关联id，可能是在第三方的uid
	 */
	private String openId;

	/**
	 * 用户id
	 */
	private Long uid;



	/**
	 * token 信息
	 */
	private String token;

	/**
	 * 渠道类型
	 */
	private Integer channelType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 状态  0-未激活  1-激活
	 * 
	 * 
	 * 这个状态是为了给大v使用，大v未入驻之前就预生成好他的账户信息。 
	 * 用第三方登录的时候，直接激活
	 */
	private Integer status ;
	
	/**
	 * 是否大v的账号
	 */
	private Integer isVip;

	

	public String getOpenId() {
		return openId;
	}



	public void setOpenId(String openId) {
		this.openId = openId;
	}



	public Long getUid() {
		return uid;
	}



	public void setUid(Long uid) {
		this.uid = uid;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public Integer getChannelType() {
		return channelType;
	}



	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getModifyTime() {
		return modifyTime;
	}



	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getIsVip() {
		return isVip;
	}



	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}



	/**
	 * 创建mobile 账户
	 * @param mobile
	 * @param password
	 * @param uid
	 * @return
	 */
	public static UserOpenAccount createMobileAccount(String mobile, String password, long uid) {

		Date now = new Date();

		UserOpenAccount account = new UserOpenAccount();

		account.setChannelType(LoginType.CHANNEL_MOBILE);
		account.setCreateTime(now);
		account.setModifyTime(now);

		account.setOpenId(mobile);

		// 设置密码
		account.setToken(MD5.md5(password));
		account.setUid(uid);
		
		
		account.setStatus(STATS_ACTIVE);
		account.setIsVip(NORMAL_ACCOUNT);
		
		
		return account;
	}
	
	
	
	/**
	 * 创建普通的第三方登录账户，非大v的账户
	 * @param mobile
	 * @param password
	 * @param uid
	 * @return
	 */
	public static UserOpenAccount createNormalOpenAccount(String openId, String token, long uid,int channelType) {

		Date now = new Date();

		UserOpenAccount account = new UserOpenAccount();

		//渠道来源
		account.setChannelType(channelType);
		account.setCreateTime(now);
		account.setModifyTime(now);
	
		
		account.setOpenId(openId);

		// 设置token
		account.setToken(token);
		
		account.setUid(uid);
		
		account.setStatus(STATS_ACTIVE);
		account.setIsVip(NORMAL_ACCOUNT);
		
		return account;
	}
	
	
	
	/**
	 * 创建普通的第三方登录账户，非大v的账户
	 * @param mobile
	 * @param password
	 * @param uid
	 * @return
	 */
	public static UserOpenAccount createVipOpenAccount(String openId, long uid, int channelType) {

		Date now = new Date();

		UserOpenAccount account = new UserOpenAccount();

		account.setChannelType(channelType);
		account.setCreateTime(now);
		account.setModifyTime(now);
	
		
		account.setOpenId(openId);

		// 设置token
		account.setToken("-1");
		
		account.setUid(uid);
		
		account.setStatus(STATS_UNACTIVE);
		account.setIsVip(VIP_ACCOUNT);
		
		return account;
	}
	

}