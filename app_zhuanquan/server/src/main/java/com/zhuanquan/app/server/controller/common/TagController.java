package com.zhuanquan.app.server.controller.common;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.author.SuggestTagResponseVo;
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
	 * @param fromIndex
	 *            从第几个开始
	 * @param limit
	 *            一次取多少个
	 * @return
	 */
	@RequestMapping(value = "/getSuggestTags", produces = { "application/json" })
	@ResponseBody
	public ApiResponse getSuggestTags(long uid, int fromIndex, int limit) {

		SuggestTagResponseVo response = tagService.getSuggestTags(uid, fromIndex, limit);
		return ApiResponse.success(response);
	}

}