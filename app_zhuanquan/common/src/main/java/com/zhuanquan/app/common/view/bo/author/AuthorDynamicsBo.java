package com.zhuanquan.app.common.view.bo.author;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zhuanquan.app.common.utils.DateUtils;
import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;

/**
 * 作者动态描述
 * @author zhangjun
 *
 */
public class AuthorDynamicsBo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6074534461785198399L;


	/**
	 * 作者动态关联的作者信息
	 */
	private List<AuthorBriefInfoBo> authorList;
	
	
	/**
	 * 平台logo
	 */
	private String platformLogoUrl;
	
	/**
	 * 平台id
	 */
	private long platformId;
	
	/**
	 * 动态的内容
	 */
	private String content;
	
	
	/**
	 * 发布时间
	 */
	private Date publicTime;
	

	
	
//	
//	public Date getPublicTime() {
//		return publicTime;
//	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public String getTimeDesc() {

		return publicTime!=null?DateUtils.formatDateDesc(publicTime):"";

	}


	public List<AuthorBriefInfoBo> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<AuthorBriefInfoBo> authorList) {
		this.authorList = authorList;
	}

	public String getPlatformLogoUrl() {
		return platformLogoUrl;
	}

	public void setPlatformLogoUrl(String platformLogoUrl) {
		this.platformLogoUrl = platformLogoUrl;
	}

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	
}