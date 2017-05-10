package com.zhuanquan.app.common.component.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  获取客户端IP保存在thread local中。 调用方法：{@link RemoteIPInterceptor#getRemoteIP()}
 * 
 */
public class RemoteIPInterceptor implements HandlerInterceptor {

    private static  final ThreadLocal<String> localIp = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        
        String ip = getRealIp(httpServletRequest);
        
        if (StringUtils.isEmpty(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }

        localIp.set(ip);
        return true;
    }


    /**
     *  获取客户端IP地址
     * @return  客户端请求IP地址
     */
    public static String getRemoteIP(){
        return localIp.get();
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
           localIp.remove();
    }
    
    
	  /**
     * 获取访问IP
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }
	

}
