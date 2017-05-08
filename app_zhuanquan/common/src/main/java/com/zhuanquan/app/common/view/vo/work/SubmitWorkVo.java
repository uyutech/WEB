package com.zhuanquan.app.common.view.vo.work;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author zhangjun
 *
 */
public class SubmitWorkVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6862230384889394358L;



	/**
	 * 作者id
	 */
	private long authorId;
	
	
	  /**
     * 作品分类
     */
	private Integer category;
	
	
	/**
	 * 作品策划list
	 */
	private List<Long> editerList;
	
	/**
	 * 出品人list
	 */
	private List<Long> producterList;


	/**
	 * 作品名称
	 */
	private String workName;

	/**
	 * 作品标题
	 */
	private String subject;
	
	
	/**
	 * 封面图片地址
	 */
	private String covPicUrl;
	
	
	/**
	 * 作品标签id list
	 */
	private List<String> workTags;
	
	
	
	
	
	
}