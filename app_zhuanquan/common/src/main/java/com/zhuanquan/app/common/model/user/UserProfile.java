package com.zhuanquan.app.common.model.user;

import java.util.Date;
import java.util.UUID;

import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;



/**
 * 用户基本信息
 * @author zhangjun
 *
 */
public class UserProfile {

	
	//注册引导第一步
	public static final int REG_STAT_BEFORE_STEP1 =0 ;
	
	////注册引导第二步
	public static final int REG_STAT_BEFORE_STEP2 =1 ;
	
	////注册引导第三步
	public static final int REG_STAT_BEFORE_STEP3 =2 ;

	//正常状态，引导完成
	public static final int REG_STAT_NORMAL =3 ;


	
	
	//正常
	public static final int STATUS_NORMAL = 1;
	
	//黑名单
	public static final int STATUS_BLACKLIST = 2;
	
	//允许被关注
	public static final int ALLOW_ATTATION = 1;
	
	//不允许被关注
	public static final int NOT_ALLOW_ATTATION = 0;

	
	private static final String DEFAULT_HEAD_URL = "";
	
	

	/**
	 * 用户id
	 */
	private Long uid;



	/**
	 * 状态 1-正常  2-黑名单
	 */
	private Integer status;

	
	
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
	 * 注册状态  3-正常状态(注册引导完成) 0-注册第一步未完成 1-注册第二步未完成  2-注册第三步未完成
	 */
	private Integer registerStat;
	
	
	
	/**
	 * 头像url
	 */
	private String headUrl;

	/**
	 * 昵称
	 */
	private String nickName;
	
	
	/**
	 * 对应作者账号id
	 */
	private Long authorId;

//	/**
//	 * 性别
//	 */
//	private Integer gender;
	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	
	

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    


	public Integer getRegisterStat() {
		return registerStat;
	}

	public void setRegisterStat(Integer registerStat) {
		this.registerStat = registerStat;
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



	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

//	public Integer getGender() {
//		return gender;
//	}
//
//	public void setGender(Integer gender) {
//		this.gender = gender;
//	}
//	
//

	
	/**
	 * 第三方登录创建个 虚拟的用户
	 * @return
	 */
	public static UserProfile registerThirdLoginUser() {

		UserProfile profile = new UserProfile();
		// profile.setUid(uid);

		Date now = new Date();
		
		String randomProfile = UUID.randomUUID().toString();
		
		profile.setStatus(UserProfile.STATUS_NORMAL);
		
		profile.setModifyTime(now);

	   //设置注册状态为 注册引导第一步
		profile.setRegisterStat(UserProfile.REG_STAT_BEFORE_STEP1);
		
		profile.setCreateTime(now);
		
		profile.setAllowAttation(UserProfile.ALLOW_ATTATION);
		
		
		profile.setNickName(randomProfile);
		
		profile.setHeadUrl(DEFAULT_HEAD_URL);

		return profile;
	}
	
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public static UserProfile createMobileRegisterRecord(RegisterRequestVo vo) {
		

		UserProfile profile = new UserProfile();
		// profile.setUid(uid);

		Date now = new Date();
		
		String randomProfile = UUID.randomUUID().toString();
		
		profile.setStatus(UserProfile.STATUS_NORMAL);
		
		profile.setModifyTime(now);

		profile.setRegisterStat(UserProfile.REG_STAT_BEFORE_STEP1);
		
		profile.setCreateTime(now);
		profile.setAllowAttation(UserProfile.ALLOW_ATTATION);
		
		
		profile.setNickName(randomProfile);
		profile.setHeadUrl(DEFAULT_HEAD_URL);
		
		
		return profile;
	}




}