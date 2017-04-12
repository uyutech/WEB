package com.zhuanquan.app.common.exception;


/**
 *
 *  API Gateway异常父类
 */
public class GatewayException extends  Exception{

    private int code;
    private String desc;

    /**
     *
     * 异常
     * @param gatewayError gateway错误码
     */
    public GatewayException(BizException gatewayError){
        this.code = gatewayError.getErrorCode();
        this.desc = gatewayError.getMessage();
    }


    /**
     */
    public GatewayException(int code, String desc){
       this.code = code;
       this.desc=desc;

    }

    @Override
    public String getMessage() {
        return "[" + this.code + ":" + this.desc + "]";
    }
    public String getDesc() {
        return desc;
    }
    public int getErrorCode() {
        return code;
    }
}
