package com.zhuanquan.app.common.model.author;

import java.util.Date;

/**
 * 第三方平台定义
 * @author zhangjun
 *
 */
public class AuthorThirdPlatformDefine {
	
	
	private Integer id;
	
	/**
	 * 1-社交类 2-音乐 3-视频 4-图片
	 */
	private Integer type;
	
	/**
	 *  第三方平台名
	 */
	private String name;
	
	
	/**
	 * logo图片
	 */
	private String logoUrl;
	
	
	/**
	 * 0-disable 1-enable
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	

	
}