package com.zhuanquan.app.common.exception;

/**
 * client_secutity 不匹配
 *
 * 
 */
public class SecurityNotMatchException extends  GatewayException {

    /**
     * 异常
     *
     */
    public SecurityNotMatchException() {
        super(500, "数据验证错误.");
    }

}
