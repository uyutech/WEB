package com.zhuanquan.app.common.exception;

/**
 *  客户端不受支持异常
 * 
 */
public class ClientNotSupportException extends  GatewayException {

    public ClientNotSupportException() {
        super(500, "不接受此平台");
    }

}

