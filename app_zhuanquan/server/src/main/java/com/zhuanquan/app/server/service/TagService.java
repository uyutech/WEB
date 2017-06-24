package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.author.SuggestTagResponseVo;

/**
 * tag 服务
 * 
 * @author zhangjun
 *
 */
public interface TagService {


	
	/**
	 * 获取推荐的tag
	 * 
	 * @param uid
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	SuggestTagResponseVo getSuggestTags(long uid,int fromIndex,int limit);

}