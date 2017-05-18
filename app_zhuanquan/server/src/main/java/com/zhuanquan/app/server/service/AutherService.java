package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo;

/**
 * 
 * @author zhangjun
 *
 */
public interface AutherService {
	
	/**
	 * 
	 * 根据作者感兴趣的领域和tag标签，获取推荐作者信息
	 * 
	 * @return
	 */
	SuggestAuthorResponseVo getSuggestAuthors(SuggestAuthorRequestVo vo);
	
}