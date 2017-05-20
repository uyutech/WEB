package com.zhuanquan.app.server.controller.user;



import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.UserService;


/**
 * 用户关注信息：关注作品，关注作者，关注标签，关注话题等等
 * 
 * 
 * @author zhangjun
 *
 */
public class UserFollowController extends BaseController {
	
	
	@Resource
	private UserService userService;
	
	/**
	 * 查询用户关注的作者信息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/queryUserFollowAuthors",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryUserFollowAuthors(long uid) {
		
		

		return ApiResponse.success();
	}
	
	
	
	
	
	
	
}