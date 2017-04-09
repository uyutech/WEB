package com.zhuanquan.app.common.model.author;

/**
 * 作者作品信息
 * 
 * @author zhangjun
 *
 */
public class Works {

	/**
	 * 作品id
	 */
	private Long workId;

	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 作品名称
	 */
	private String workName;

	/**
	 * 作品标题
	 */
	private String subject;

	/**
	 * 收藏数
	 */
	private Integer favNum;

	/**
	 * 评论数
	 */
	private Integer commentNum;

	/**
	 * 转发数
	 */
	private Integer transpondNum;

	/**
	 * 点赞数
	 */
	private Integer upvoteNum;

	/**
	 * 点击阅读数
	 */
	private Integer readNum;

	/**
	 * 封面图片地址
	 */
	private String covPicUrl;
	
	/**
	 * 作品标签 多个以 逗号分割  动漫，悬疑
	 */
	private String workTags;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getFavNum() {
		return favNum;
	}

	public void setFavNum(Integer favNum) {
		this.favNum = favNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getTranspondNum() {
		return transpondNum;
	}

	public void setTranspondNum(Integer transpondNum) {
		this.transpondNum = transpondNum;
	}

	public Integer getUpvoteNum() {
		return upvoteNum;
	}

	public void setUpvoteNum(Integer upvoteNum) {
		this.upvoteNum = upvoteNum;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public String getCovPicUrl() {
		return covPicUrl;
	}

	public void setCovPicUrl(String covPicUrl) {
		this.covPicUrl = covPicUrl;
	}

	public String getWorkTags() {
		return workTags;
	}

	public void setWorkTags(String workTags) {
		this.workTags = workTags;
	}

	
}