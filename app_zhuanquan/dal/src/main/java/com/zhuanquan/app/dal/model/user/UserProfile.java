package com.zhuanquan.app.dal.model.user;

import java.util.Date;

/**
 * 用户基本信息
 * @author zhangjun
 *
 */
public class UserProfile {
	
	//删除状态，比如第三方登录之后，绑定手机，如果手机原来已经注册了，用户需要决定舍弃一个账号做合并
	public static final int DELETE_STATUS_DELETE = 0;
	
	//正常状态
	public static final int DELETE_STATUS_NOT_DELETE = 1;

	
	
	//正常
	public static final int STATUS_NORMAL = 1;
	
	//黑名单
	public static final int STATUS_BLACKLIST = 2;
	
	//允许被关注
	public static final int ALLOW_ATTATION = 1;
	
	//不允许被关注
	public static final int NOT_ALLOW_ATTATION = 0;

	

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 状态 1-正常  2-黑名单
	 */
	private Integer status;

    /**
     * 手机号
     */
    private String mobile;
	
	
	/**
	 * 是否允许关注
	 */
	private Integer allowAttation;
	
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 是否删除  1-未删除   0-删除
	 */
	private int isDelete;


	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAllowAttation() {
		return allowAttation;
	}

	public void setAllowAttation(Integer allowAttation) {
		this.allowAttation = allowAttation;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}




}