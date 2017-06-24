package com.zhuanquan.app.server.controller.author;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.bo.work.AuthorAlbumInfoBo;
import com.zhuanquan.app.common.view.bo.work.AuthorWorkInfoBo;
import com.zhuanquan.app.common.view.vo.author.AuthorHomeInfoResponseVo;
import com.zhuanquan.app.common.view.vo.author.AuthorRelationshipVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo;
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
	
	
	/**
	 * 作者主页的基础信息
	 * @param authorId
	 * @return
	 */
	@RequestMapping(value = "/queryAuthorHomePageInfo",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryAuthorHomePageInfo(long authorId) {	
		
		

		AuthorHomeInfoResponseVo vo;
		
		
		return ApiResponse.success();
		
	}
	
	
	/**
	 * 分页查询作者的作品
	 * @param authorId
	 * @return
	 */
	@RequestMapping(value = "/pageQueryAuthorWorks",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryAuthorWorks(long authorId,int fromIndex,int limit) {	

		List<AuthorWorkInfoBo>  list;
		
		return ApiResponse.success();
		
	}
	
	
	
	/**
	 * 分页查询作者的专辑
	 * @param authorId
	 * @return
	 */
	@RequestMapping(value = "/pageQueryAuthorAlbums",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryAuthorAlbums(long authorId,int fromIndex,int limit) {	

		List<AuthorAlbumInfoBo>  list;
		
		return ApiResponse.success();
		
	}	
	

	
	/**
	 * 分页查询作者的人际关系(组合，公司，合作者)
	 * @param authorId
	 * @return
	 */
	@RequestMapping(value = "/pageQueryAuthorRelationship",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryAuthorRelationship(long authorId,int fromIndex,int limit) {	

		AuthorRelationshipVo  vo;
		
		return ApiResponse.success();
		
	}
	

	
	/**
	 * 分页查询作者的动态
	 * @param authorId
	 * @return
	 */
	@RequestMapping(value = "/pageQueryAuthorDynamics",produces = {"application/json"})
	@ResponseBody
	public ApiResponse pageQueryAuthorDynamics(long authorId,int fromIndex,int limit) {	

		
		return ApiResponse.success();
		
	}
	
	
	
	
}