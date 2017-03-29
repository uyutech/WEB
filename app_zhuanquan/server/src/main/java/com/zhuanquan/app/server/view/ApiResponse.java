package com.zhuanquan.app.server.view;

import java.io.Serializable;

import com.framework.core.error.exception.BizException;
import com.framework.core.error.exception.code.impl.BaseCode;

/**
 * 
 * 
 * 
 * @author zhangjun
 *
 */
public class ApiResponse implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2555972394114423024L;

    public static final int CODE_SUCCESS = 0;
    
    public static final int CODE_FAILED = 1;
    /**
     * 结果  0-成功  1-失败
     */
    private int result;
    
    /**
     *  msg
     */
    private String message = "";
    
    /**
     * data
     */
    private Object data;
    
    /**
     * 错误码，默认为1000，表示成功；发生异常时会有专门的异常码
     */
    private int errorCode= BaseCode.EX_SYSTEM_SUCCESS.getCode();

    private ApiResponse(int result, Object data, String message) {
        
        this.result = result;
        this.message = message;
        this.data = data;
    }
    
    private ApiResponse(int result, Object data, String message,int errorcode) {
        this.result = result;
        this.message = message;
        this.data = data;
        this.errorCode = errorcode;
    }

//    
//    public int getResult() {
//    
//        return result;
//    }


    public int getErrorCode() {
    
        return errorCode;
    }


    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return CODE_SUCCESS == result;
    }

    public static final ApiResponse success() {
        return new ApiResponse(CODE_SUCCESS, null, null);
    }

    public static final ApiResponse success(Object data) {
        return new ApiResponse(CODE_SUCCESS, data, null);
    }

    public static final ApiResponse success(Object data, String message) {
        return new ApiResponse(CODE_SUCCESS, data, message);
    }

    public static final ApiResponse failed() {
        return new ApiResponse(CODE_FAILED, null, null);
    }

    public static final ApiResponse failed(String message) {
        return new ApiResponse(CODE_FAILED, null, message);
    }

    public static final ApiResponse failed(Object data, String message) {
        return new ApiResponse(CODE_FAILED, data, message);
    }

    
    public static final ApiResponse failed(Object obj, String message, int errorcode) {
        return new ApiResponse(CODE_FAILED, obj, message,errorcode);
    }
    
    public static final ApiResponse failed(BizException e) {
        return new ApiResponse(CODE_FAILED, null, e.getMessage(),e.getErrorCode());
    }
    

    
}
