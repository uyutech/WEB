package com.zhuanquan.app.server.controller.user;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.server.service.UpvoteService;

/**
 * 用户点赞的controller
 * 
 * @author zhangjun
 *
 */
@Controller
@RequestMapping(value = "/upvote")
public class UserUpvoteController {
	
	@Resource
	private UpvoteService upvoteService;
	


	/**
	 * 对作品点赞,不允许在事务中调用!!!!因为这里是先写了缓存，没直接写库
	 * @param uid uid
	 * @param workId 作品id
	 */
	
	@RequestMapping(value = "/doUpvoteWork")
	@ResponseBody
	public ApiResponse doUpvoteWork(long uid,long workId){
		
		upvoteService.upvoteWork(uid, workId);
		
		return ApiResponse.success();

	}
	
	/**
	 * 
	 * 
	 * 取消对作品的点赞，对作品点赞,不允许在事务中调用!!!!因为这里是先写了缓存，没直接写库。
	 * 
	 * @param uid
	 * @param workId
	 */
	@RequestMapping(value = "/cancelUpvoteWork")
	@ResponseBody
	public ApiResponse cancelUpvoteWork(long uid,long workId){
		upvoteService.cancelUpvoteWork(uid, workId);
		return ApiResponse.success();

		
	}
	
	
	/**
	 * 查询作品的点赞数
	 * 
	 * @param workId
	 * @return
	 */
	
	@RequestMapping(value = "/queryWorkUpvoteNum")
	@ResponseBody
	public ApiResponse queryWorkUpvoteNum(long workId) {
		
		long num = upvoteService.queryWorkUpvoteNum(workId);
		
		return ApiResponse.success(num);

	}
	
	
	
	/**
	 * 是否点赞了
	 * @param uid
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/queryWorkUpvoteNum")
	@ResponseBody
	public ApiResponse hasUpvoteWork(long uid,long workId) {
		
		boolean isUpvote = upvoteService.hasUpvoteWork(uid, workId);
		
		return ApiResponse.success(isUpvote?1:0);

	}
	
}