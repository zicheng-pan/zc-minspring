package com.minispring.web.servlet;

import com.minispring.web.RequestMapping;
import com.minispring.web.RequestMethod;
import com.minispring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    /**
     * TODO 这里处理request参数
     *
     * @param request
     * @param response
     * @param handler
     */
    private void handleInternal(HttpServletRequest request, HttpServletResponse response,
                                HandlerMethod handler) {
        Method method = handler.getMethod();
        boolean isRequestMapping =
                method.isAnnotationPresent(RequestMapping.class);
        //如果该方法带有@RequestMapping注解,则建立映射关系
        if (isRequestMapping) {
            RequestMethod[] http_method = method.getAnnotation(RequestMapping.class).method();
            if (http_method.length == 0) {
                handleRequest(response, handler);
            }
            else if (Arrays.asList(http_method).contains(RequestMethod.valueOf(request.getMethod()))) {
                handleRequest(response, handler);
            } else {
                webwrite(response, "http method does not match exception");
            }
        }
    }

    public void handleRequest(HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object objResult = null;
        try {
            Object obj = handler.getBean();
            objResult = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        webwrite(response, objResult);
    }

    public void webwrite(HttpServletResponse response, Object content) {
        try {
            response.getWriter().append(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
