package com.zhuanquan.app.server.aop;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.sesssion.SessionHolder;
import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.utils.IpUtils;
import com.zhuanquan.app.server.controller.common.BaseController;

/**
 * aop用户请求日志
 */
public class UserRequestLogger extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserRequestLogger.class);

    public Object loggerUserRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        try {

            StopWatch clock = new StopWatch();
            clock.start(); // 计时开始
            Object retVal = joinPoint.proceed();
            clock.stop(); // 计时结束

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request == null) {
                return retVal;
            }
//
//            String sessionId = null;
//            
//            HttpSession session = request.getSession(false);
//            if (session != null) {
//                sessionId = session.getId();
//            }

            //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
            Object[] args = joinPoint.getArgs();
            StringBuffer classUrl = new StringBuffer(request.getRequestURI());
            StringBuffer classvalue = new StringBuffer();

            try {

                if (args != null && args.length > 0) {
                    for (Object object : args) {
                        if (object == null) {
                            continue;
                        }
                        Class clazz = object.getClass();// 获取集合中的对象类型

                        if (HttpServletRequest.class == clazz || javax.servlet.http.HttpServletResponse.class == clazz
                                || clazz.toString().indexOf("org.springframework") >= 0) {
                            continue;
                        }

                        if (clazz.getDeclaredConstructors() != null && clazz.getDeclaredConstructors().length > 0) {
                            classvalue.append(clazz.getDeclaredConstructors()[0].getName() + "=");
                        }

                        if (String.class == clazz || Long.class == clazz || Boolean.class == clazz
                                || Double.class == clazz || Integer.class == clazz || Short.class == clazz
                                || Float.class == clazz) {
                            classvalue.append(object + ",");
                        } else {

                            Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
                            if (fds != null && fds.length > 0) {
                                classvalue.append("{");
                                for (Field field : fds) {// 遍历该数组
                                    if (field == null) {
                                        continue;
                                    }
                                    try {
                                        String fdname = field.getName();// 得到字段名，
                                        Method metd = clazz.getMethod("get" + change(fdname), null);// 根据字段名找到对应的get方法，null表示无参数
                                        Object name = metd.invoke(object, null);// 调用该字段的get方法
                                        if (name != null) {
                                            classvalue.append(fdname + ":" + name + ",");
                                        }
                                    } catch (NoSuchMethodException e) {
                                    } catch (SecurityException e) {
                                    } catch (IllegalAccessException e) {
                                    } catch (IllegalArgumentException e) {
                                    } catch (InvocationTargetException e) {
                                    }
                                }
                                classvalue = new StringBuffer(classvalue.substring(0, classvalue.length() - 1)).append("},");
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
                logger.error("error:====inner error=====" + e.getMessage());
            }

            if(classvalue!= null && classvalue.length()>0){
               classvalue= new StringBuffer(classvalue.substring(0, classvalue.length() - 1)); 
            }


            StringBuffer logStr = new StringBuffer(IpUtils.getIpAddr(request) + "|");
            
            String returnValue = JSON.toJSONString(retVal);
            
            logStr.append(classUrl).append("|").append(classvalue).append("|returnValie=")
            	.append(returnValue).append("|time=").append(clock.getTotalTimeMillis());
            
            UserSession session = SessionHolder.getCurrentLoginUserSession();
            
            if(session == null) {
            	logStr.append("|").append("session is null");
            } else {
            	logStr.append("|uid=").append(session.getUid()).append("|openId=").append(session.getOpenId()).append("|channel=")
            	.append(session.getChannelType());
            }
            
            logger.info(logStr.toString());
            return retVal;
        } catch (Exception e) {
            logger.error("error:====error==============" + e.getMessage());
        }

        return null;  
    }


    /**
     * @param src 源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    private static String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }

}
