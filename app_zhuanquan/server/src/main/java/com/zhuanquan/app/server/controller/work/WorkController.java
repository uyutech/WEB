package com.zhuanquan.app.server.controller.work;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.work.WorkDetailInfoVo;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.WorkService;


@Controller
@RequestMapping(value = "/work")
public class WorkController extends BaseController {
	
	@Resource
	private WorkService workService;
	
	@RequestMapping(value="/queryWorkDetailInfo",produces = {"application/json"})
	@ResponseBody
	public ApiResponse queryWorkDetailInfo(long workId) {
		
		
		WorkDetailInfoVo vo = workService.queryWorkDetail(workId);
		
		return ApiResponse.success(vo);
		
	}
	
	
}