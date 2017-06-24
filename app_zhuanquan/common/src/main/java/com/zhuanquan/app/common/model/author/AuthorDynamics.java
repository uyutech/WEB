package com.zhuanquan.app.common.model.author;

import java.util.Date;

import org.springframework.util.Assert;

import com.zhuanquan.app.common.constants.author.AuthorDynamicOperTypeEnum;

/**
 * 作者动态
 * 
 * @author zhangjun
 *
 */
public class AuthorDynamics {

	private Long id;
	
	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 操作类型
	 */
	private Integer operType;

	/**
	 * 第三方平台id AuthorThirdPlatformDefine.id
	 * 
	 * 
	 */
	private Integer platformId;

	/**
	 * 目标
	 */
	private String target;

	/**
	 * 扩展信息
	 */
	private String extendInfo;

	/**
	 * 0-disable 1-enable
	 */
	private Integer status;

	/**
	 * 发布时间
	 */
	private Date publishTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	/**
	 * 获取动态的内容
	 * @param define
	 * @return
	 */
	public static String getContent(AuthorDynamics dynamics) {
		
		
		StringBuilder sb = new StringBuilder();
		
		String operDesc = AuthorDynamicOperTypeEnum.getOperTypeDesc(dynamics.getOperType());
		
		Assert.notNull(operDesc);
		
		sb.append(operDesc);  //发布了一首新歌

		sb.append(" ");
		sb.append(dynamics.getTarget());  

		return sb.toString();
		
	}

}