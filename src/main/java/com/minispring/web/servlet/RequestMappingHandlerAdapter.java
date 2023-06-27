package com.minispring.web.servlet;

import com.minispring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class RequestMappingHandlerAdapter implements HandlerAdapter{
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
     * @param request
     * @param response
     * @param handler
     */
    private void handleInternal(HttpServletRequest request, HttpServletResponse response,
                                HandlerMethod handler) {
        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object objResult = null;
        try {
            objResult = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().append(objResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
