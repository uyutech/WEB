package com.zhuanquan.app.server.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.dal.model.user.UserProfile;
import com.zhuanquan.app.server.view.ResponseResult;

@Controller
public class UserController {


	@Resource
	private UserProfileDAO userProfileDAO;
	
	
	@RequestMapping(value="/user/queryProfileByUid")
	@ResponseBody
	public ResponseResult  queryProfileByUid(long uid) {
		
		
		UserProfile profile = userProfileDAO.queryById(uid);
		
		return ResponseResult.success(profile);
	}
	
	
}