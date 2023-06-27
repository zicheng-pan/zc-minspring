package com.minispring.web.servlet;

import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.web.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    String sContextConfigLocation;

    private WebApplicationContext webApplicationContext;

    private RequestMappingHandlerMapping handlerMapping;

    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 把Listener启动的上下文和DispatcherServlet启动的上下文两者区分开
     * 按照时序关系，Listener启动在前，对应的上下文我们叫parentApplicationContext
     */
    private WebApplicationContext parentApplicationContext;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.parentApplicationContext = (WebApplicationContext)
                this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        this.webApplicationContext = new
                AnnotationConfigWebApplicationContext(sContextConfigLocation,
                this.parentApplicationContext);

        refresh();
    }

    protected void refresh() {
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
    }


    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse
            response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, response, handlerMethod);
    }
}
