package com.zhuanquan.app.common.model.work;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 作品基本信息
 * 
 * @author zhangjun
 *
 */
public class WorkBase {

	//待审核
	public static final int STAT_AUDIT = 0;
	
	/**
	 * 可用
	 */
	public static final int STAT_ENABLE = 1;
	
	//不可用
	public static final int STAT_DISABLE = 2;


	
	
	/**
	 * 作品id
	 */
	private Long workId;


	/**
	 * 作品标题
	 */
	private String subject;

	/**
	 * 作品简介
	 */
	private String summary;

	/**
	 * 封面图片地址
	 */
	private String covPicUrl;
	
	/**
	 * 0-待审核 1-正常  2-disable
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



	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}



	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getCovPicUrl() {
		return covPicUrl;
	}

	public void setCovPicUrl(String covPicUrl) {
		this.covPicUrl = covPicUrl;
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


	/**
	 * 
	 * @param subject
	 * @param summary
	 * @param covPicUrl
	 * @return
	 */
	public static WorkBase createWorkBase(String subject,String summary,String covPicUrl){
		
		WorkBase base = new WorkBase();
		
		Assert.hasText(subject);
		
		
		base.setSubject(subject);

		base.setCovPicUrl(StringUtils.isEmpty(covPicUrl)?null:covPicUrl);
		base.setSummary(StringUtils.isEmpty(summary)?null:summary);
		
		//待审核状态
		base.setStatus(STAT_AUDIT);
		
		Date now = new Date();
		base.setCreateTime(now);
		base.setModifyTime(now);
		
		return base;
		
	}
	
	
	 
	
}