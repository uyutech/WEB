package com.zhuanquan.app.common.view.vo.work;



import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.TagInfoBo;


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
	 * 作者的角色视图
	 */
	private List<WorkAttenderRoleViewVo> authorList;
	
	/**
	 * 作品简介
	 */
	private String summary;
	
	
	/**
	 * 作品标签列表
	 */
	private List<TagInfoBo> tagList;
	

	
	/**
	 * 点赞数
	 */
	private long upvoteNum;
	
	/**
	 * 收藏数
	 */
	private long favNum;
	
	
	/**
	 * 转发数
	 */
	private long forwardNum;
	
	
	
	
	
	
}