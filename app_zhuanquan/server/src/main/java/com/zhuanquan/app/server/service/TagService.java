package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;

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
	 * 
	 * @return
	 */
	List<SuggestTagVo> getSuggestTags(long uid);

}