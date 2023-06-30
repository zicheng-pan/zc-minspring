package com.minispring.web.servlet;

import com.minispring.beans.factory.exception.BeansException;
import com.minispring.web.RequestMapping;
import com.minispring.web.RequestMethod;
import com.minispring.web.WebApplicationContext;
import com.minispring.web.databinder.WebBindingInitializer;
import com.minispring.web.databinder.WebDataBinder;
import com.minispring.web.databinder.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    WebApplicationContext wac;

//    private WebBindingInitializer webBindingInitializer = null;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
//        try {
//            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }

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
            if (http_method.length == 0 ||
                    Arrays.asList(http_method).contains(RequestMethod.valueOf(request.getMethod()))) {
                handleRequest(request, response, handler);
            } else {
                webwrite(response, "http method does not match exception");
            }
        }
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {

        WebDataBinderFactory binderFactory = new WebDataBinderFactory();

        Method method = handler.getMethod();

        Parameter[] methodParameters = method.getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = null;
            try {
                methodParamObj = methodParameter.getType().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
//            webBindingInitializer.initBinder(wdb);
            wdb.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }


        Object objResult = null;
        try {
            objResult= method.invoke(handler.getBean(), methodParamObjs);
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
