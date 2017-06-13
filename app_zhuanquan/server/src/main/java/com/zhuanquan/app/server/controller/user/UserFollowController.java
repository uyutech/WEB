package com.zhuanquan.app.server.controller.user;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.QueryUserFollowAuthorsResponseVo;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.UserFollowService;
import com.zhuanquan.app.server.service.impl.UserFollowServiceImpl;


/**
 * 用户关注信息：关注作品，关注作者，关注标签，关注话题等等
 * 
 * 
 * @author zhangjun
 *
 */
@Controller
@RequestMapping(value="/follow")
public class UserFollowController extends BaseController {
	
	
	@Resource
	private UserFollowService userFollowService;
	
	/**
	 * 查询用户关注的作者信息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/queryUserFollowAuthors",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryUserFollowAuthors(long uid) {
		
		QueryUserFollowAuthorsResponseVo response = userFollowService.queryFollowAuthors(uid);
		
		return ApiResponse.success(response);
	}
	
	
	/**
	 * 关注作者
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/followAuthor",produces = {"application/json"})
	@ResponseBody
	public ApiResponse followAuthor(long uid,long authorId) {

		userFollowService.followAuthor(uid, authorId);
		
		return ApiResponse.success();
	}	
	
	

	
	/**
	 * 取消关注
	 * @param uid
	 * @param authorId
	 */
	@RequestMapping(value="/cancelFollowAuthor",produces = {"application/json"})
	@ResponseBody
	public ApiResponse cancelFollowAuthor(long uid,long authorId){
		
		userFollowService.cancelFollowAuthor(uid, authorId);
		
		return ApiResponse.success();

	}

	
}