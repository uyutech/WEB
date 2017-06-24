package com.zhuanquan.app.server.controller.work;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.work.WorkDetailInfoVo;
import com.zhuanquan.app.server.controller.common.BaseController;


@Controller
@RequestMapping(value = "/work")
public class WorkController extends BaseController {
	
	
	
	@RequestMapping(value="/queryWorkDetailInfo",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryWorkDetailInfo(long workId) {
		
		
		WorkDetailInfoVo vo;
		
		return ApiResponse.success();
		
	}
	
	
}