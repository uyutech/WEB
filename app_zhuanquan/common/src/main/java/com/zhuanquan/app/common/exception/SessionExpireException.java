package com.zhuanquan.app.common.exception;

/**
 * 
 */
public class SessionExpireException extends  GatewayException {

    public SessionExpireException() {
        super(401, "登录会话超时,请退出重新登录.");
    }

}
