package com.zhuanquan.app.server.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.view.ApiResponse;

//基类，统一处理所有的异常
public class BaseController {

	/**
	 * cotroller 都是单例，不用static字段
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	/** 基于@ExceptionHandler异常处理 */
//	@ExceptionHandler
//	@ResponseBody
//	public String handleAndReturnData(HttpServletRequest request, HttpServletResponse response, Exception ex) {
//
//		Map map = request.getParameterMap();
//
//		if (ex instanceof BizException) {
//
//			BizException bizEx = (BizException) ex;
//
//			logger.error(request.getRequestURI() + " trigger BizException,[parm]=" + JSON.toJSONString(map)
//					+ ",[message]:" + bizEx.getMessage(), ex);
//
//			return JSON.toJSONString(ApiResponse.failed(bizEx));
//
//		} else {
//
//			logger.error(request.getRequestURI() + " trigger unexpected exception,[parm]=" + JSON.toJSONString(map)
//					+ ",[message]:" + ex.getMessage(), ex);
//
//			return JSON.toJSONString(ApiResponse.failed(BizErrorCode.EX_UNEXPECTED_ERROR.getCode(), "非预期异常"));
//		}
//
//	}
//	
//	
//	


}
