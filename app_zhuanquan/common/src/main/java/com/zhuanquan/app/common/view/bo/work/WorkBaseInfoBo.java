package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;

/**
 * 作品基本信息
 * @author zhangjun
 *
 */
public class WorkBaseInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979816331534360600L;
	
	
	/**
	 * 作品id
	 */
	private Long workId;

    /**
     * 作品分类
     */
	private Integer category;
	
	
	/**
	 * 策划信息，以逗号分割，格式为 作者名A(作者A id),作者名B(作者B的id)
	 */
	private String editors;
	
	
	
	/**
	 * 出品人信息，以逗号分割，格式为 作者名A(作者A id),作者名B(作者B的id)
	 */
	private String producters;
	

	/**
	 * 作品简介
	 */
	private String summary;

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
	
	
	
	
	
	
}