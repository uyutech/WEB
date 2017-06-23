package com.zhuanquan.app.server.controller.sync;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;
import com.zhuanquan.app.server.controller.common.BaseController;

/**
 * 同步数据的controller
 * 
 * @author zhangjun
 *
 */
@Controller
@RequestMapping(value = "/syncdata")
public class SyncDataController extends BaseController {

	@RequestMapping(value = "/importWorkInfo", produces = { "application/json" })
	@ResponseBody
	public ApiResponse importWorkInfo(String workInfoJson) {

		ImportWorkInfoVo vo = parseWorkInfoJson(workInfoJson);

		
		
		
		return null;

	}

	private ImportWorkInfoVo parseWorkInfoJson(String workInfoJson) {

		if (StringUtils.isEmpty(workInfoJson)) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		ImportWorkInfoVo vo;
		try {
			vo = JSON.parseObject(workInfoJson, ImportWorkInfoVo.class);

			if (vo == null) {
				throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

	}

}