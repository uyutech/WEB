package com.zhuanquan.app.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   http utils
 * 
 */
public class HttpRequestUtils {

    public static Map<String, Object> getRequestParams(HttpServletRequest request){

        Map<String, Object> simpleMap = new HashMap<>();

        if(request.getParameterMap() != null) {
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                String value = ArrayUtils.isEmpty(entry.getValue()) ? null : entry.getValue()[0];
                simpleMap.put(entry.getKey(), value);
            }
        }

        return simpleMap;
    }


    /**
     * 获取服务名称，从来自gateway的http请求中获取. 如果是method=xxx，则用这个值。如果是 /guang/getaaa/ 则用这个
     * @param request 请求
     * @return 服务名称
     */
    public final static String getGatewayServiceName(HttpServletRequest request) {
        //如果直接请求/gateway 或者/，则取method请求参数作为method
        final List<String> DEFAULT_CONTEXT = Lists.newArrayList("/", "/gateway", "/gateway/");
        String requestURI = request.getRequestURI();
        if (DEFAULT_CONTEXT.contains(requestURI)) {
            return request.getParameter("method");
        }
        return requestURI;
    }
}
