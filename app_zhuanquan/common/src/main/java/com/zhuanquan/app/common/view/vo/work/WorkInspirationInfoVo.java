package com.zhuanquan.app.common.view.vo.work;

import java.io.Serializable;
import java.util.Date;

import com.zhuanquan.app.common.utils.DateUtils;

/**
 * 创作灵感
 * @author zhangjun
 *
 */
public class WorkInspirationInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8453367101574910450L;
	
	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 头像地址
	 */
	private String headUrl;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	/**
	 * 创作灵感
	 */
	private String inspiration;


	public long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}


	public String getHeadUrl() {
		return headUrl;
	}


	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}




	/**
	 * 发布时间
	 * @return
	 */
	public String getPublishTimeDesc(){
		
		return createTime!=null?DateUtils.formatDateDesc(createTime):"";
	}
	
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getInspiration() {
		return inspiration;
	}


	public void setInspiration(String inspiration) {
		this.inspiration = inspiration;
	}
	
	
	
	
}