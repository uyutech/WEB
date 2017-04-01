//package com.zhuanquan.app.server.base.filter.wrapper;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//
//
///**
// * 使用redis缓存 session
// * 
// * @author zhangjun
// *
// */
//public class SessionRequestWrapper extends XSSRequestWrapper {
//
//	public SessionRequestWrapper(HttpServletRequest servletRequest) {
//		super(servletRequest);
//	}
//
//	@Override
//	public HttpSession getSession() {
//      SessionProxyHandler.getInstance(super.getSession());
//	}
//
//	@Override
//	public HttpSession getSession(boolean create) {
//
//		return SessionProxyHandler.getInstance(super.getSession(create));
//	}
//}