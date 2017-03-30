package com.zhuanquan.app.dal.model.user;

import java.io.Serializable;
import java.util.Date;

import com.framework.core.common.utils.MD5;
import com.zhuanquan.app.common.constants.ChannelType;

public class UserOpenAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711050830921914903L;

	/**
	 * 
	 */
	private String openId;

	/**
	 * 用户id
	 */
	private long uid;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * token 信息
	 */
	private String token;

	/**
	 * 渠道类型
	 */
	private int channelType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

		account.setChannelType(ChannelType.CHANNEL_MOBILE);
		account.setCreateTime(now);
		account.setModifyTime(now);
		account.setNickName(mobile);

		account.setOpenId(mobile);

		// 设置密码
		account.setToken(MD5.md5(password));
		account.setUid(uid);
		
		return account;
	}

}