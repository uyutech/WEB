package com.zhuanquan.app.common.view.vo.work;



import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.TagInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkMilestoneBo;


/**
 * 作品详细信息
 * @author zhangjun
 *
 */
public class WorkDetailInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433617228434411333L;
	
	
	
	/**
	 * 作品id
	 */
	private long workId;
	
	
	/**
	 * 作品名
	 */
	private String workName;
	
	
	/**
	 * 作品封面
	 */
	private String covPicUrl;
	
	
	/**
	 * 作者的角色视图
	 */
	private List<WorkAttenderRoleViewVo> authorList;
	
	/**
	 * 作品简介
	 */
	private String summary;
	
	
	/**
	 * 总评论数
	 */
	private long totalComment;
	
	/**
	 * 作品标签列表
	 */
	private List<TagInfoBo> tagList;
	
	
	/**
	 * 多媒体资源
	 */
	private List<WorkMediaSourceCategoryView> mediaSources;

	
	/**
	 * 创作灵感
	 */
	private List<WorkInspirationInfoVo> workInspirationList;
	

	/**
	 * 作品创作里程碑
	 */
	private List<WorkMilestoneBo> milestoneList;
	
	
	
	

	public List<WorkMilestoneBo> getMilestoneList() {
		return milestoneList;
	}


	public void setMilestoneList(List<WorkMilestoneBo> milestoneList) {
		this.milestoneList = milestoneList;
	}


	public long getTotalComment() {
		return totalComment;
	}


	public void setTotalComment(long totalComment) {
		this.totalComment = totalComment;
	}


	public List<WorkInspirationInfoVo> getWorkInspirationList() {
		return workInspirationList;
	}


	public void setWorkInspirationList(List<WorkInspirationInfoVo> workInspirationList) {
		this.workInspirationList = workInspirationList;
	}


	public long getWorkId() {
		return workId;
	}


	public void setWorkId(long workId) {
		this.workId = workId;
	}


	public String getCovPicUrl() {
		return covPicUrl;
	}


	public void setCovPicUrl(String covPicUrl) {
		this.covPicUrl = covPicUrl;
	}


	public String getWorkName() {
		return workName;
	}


	public void setWorkName(String workName) {
		this.workName = workName;
	}


	public List<WorkAttenderRoleViewVo> getAuthorList() {
		return authorList;
	}


	public void setAuthorList(List<WorkAttenderRoleViewVo> authorList) {
		this.authorList = authorList;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public List<TagInfoBo> getTagList() {
		return tagList;
	}


	public void setTagList(List<TagInfoBo> tagList) {
		this.tagList = tagList;
	}


	public List<WorkMediaSourceCategoryView> getMediaSources() {
		return mediaSources;
	}


	public void setMediaSources(List<WorkMediaSourceCategoryView> mediaSources) {
		this.mediaSources = mediaSources;
	}
	

	
}