package com.zhuanquan.app.server.controller.author;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo;
import com.zhuanquan.app.common.view.vo.user.LoginRequestVo;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.AutherService;

/**
 *  作者controller
 * @author zhangjun
 *
 */

@Controller
@RequestMapping(value = "/author")
public class AuthorController extends BaseController {
	
	@Resource
	private AutherService autherService;
	
	
	/**
	 * 
	 * 根据用户感兴趣的领域和标签，推荐作者信息
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getSuggestAuthors",produces = {"application/json"})
	@ResponseBody
	public ApiResponse getSuggestAuthors(long uid, int fromIndex, int limit) {

		this.checkLoginUid(uid);
		
		//当前登录方式
		int channel = SessionHolder.getCurrentLoginUserInfo().getLoginType();
		
		SuggestAuthorRequestVo request = new SuggestAuthorRequestVo();
		request.setChannelType(channel);
		request.setFromIndex(fromIndex);
		request.setLimit(limit);
		request.setUid(uid);
		
		SuggestAuthorResponseVo vo = autherService.getSuggestAuthors(request);

		return ApiResponse.success(vo);
		
	}
	

	
	
}