package com.zhuanquan.app.common.model.work;

import java.util.Date;

/**
 * 作品内容详情
 * @author zhangjun
 *
 */
public class WorkMediaDetail {
	
	/**
	 * 内容详情id
	 */
	private Long contentId;
	
	
	/**
	 * 内容类型  0-音频  1-视频 2-图片 
	 */
	private Integer contentType;
	
	
	/**
	 * 音频视频图片的url
	 */
	private String content;
	
	
	/**
	 * 状态 0-废弃   1-启用
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
	
	
	
	
}