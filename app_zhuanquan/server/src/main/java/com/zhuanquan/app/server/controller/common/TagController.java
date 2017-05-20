package com.zhuanquan.app.server.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.constants.RegisterFlowConstants;
import com.zhuanquan.app.common.view.ApiResponse;
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
	@RequestMapping(value = "/getSuggestTags",produces = {"application/json"})
	@ResponseBody
	public ApiResponse getSuggestTags(long uid,int pageNum) {
		List<SuggestTagVo> list = tagService.getSuggestTags(uid,pageNum,RegisterFlowConstants.REG_SUGGEST_TAG_PAGE_SIZE);
		return ApiResponse.success(list);
	}



}