package com.zhuanquan.app.server.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorVo;
import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;
import com.zhuanquan.app.server.service.TagService;

/**
 * 
 * @author zhangjun
 *
 */

@Controller
@RequestMapping(value = "/tag")
public class TagController {

	@Resource
	private TagService tagService;
	
	
	/**
	 * 获取推荐的标签
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getSuggestTags")
	@ResponseBody
	public ApiResponse getSuggestTags(long uid) {

		List<SuggestTagVo> list = tagService.getSuggestTags(uid);
		
		return ApiResponse.success(list);
	}



}