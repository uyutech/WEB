package com.zhuanquan.app.server.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {
	
	/**
	 * cotroller 都是单例，不用static字段
	 */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

}
