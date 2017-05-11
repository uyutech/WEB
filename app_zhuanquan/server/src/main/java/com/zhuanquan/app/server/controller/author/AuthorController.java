package com.zhuanquan.app.server.controller.author;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.server.service.AutherService;

/**
 *  作者controller
 * @author zhangjun
 *
 */

@Controller
@RequestMapping(value = "/author")
public class AuthorController {
	
	@Resource
	private AutherService autherService;
	
	
	/**
	 * 
	 * 根据用户感兴趣的领域和标签，推荐作者信息
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getSuggestAuthors")
	@ResponseBody
	public ApiResponse getSuggestAuthors(long uid) {
		

		List<SuggestAuthorVo> list = autherService.getSuggestAuthors(uid);
		
		
		return ApiResponse.success(list);
		
	}
	
	
	
	
	
	
}