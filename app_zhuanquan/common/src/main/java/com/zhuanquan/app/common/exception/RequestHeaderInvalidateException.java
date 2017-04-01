package com.zhuanquan.app.common.exception;


/**
 *  请求头不正确
 * 
 */
public class RequestHeaderInvalidateException extends  GatewayException {

    /**
     * 异常
     *
     */
    public RequestHeaderInvalidateException(String headerName) {
        super(500, "缺少"+headerName);
    }

}
