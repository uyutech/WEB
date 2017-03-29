package com.zhuanquan.app.common.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  utils
 */
public class ServletUtils {


    

    public final static Map<String, String> getRequestParams(HttpServletRequest httpServletRequest) {
            Map<String, String> map = new HashMap<>();
            Enumeration paramNames = httpServletRequest.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String key = (String) paramNames.nextElement();
                String value = httpServletRequest.getParameter(key);
                map.put(key, value);
            }
            return map;
    }

}
