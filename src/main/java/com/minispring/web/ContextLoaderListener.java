package com.minispring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void initWebApplicationContext(ServletContext servletContext) {
        /**
         * 这里需要指定applicationContext.xml文件的路径，通过Servlet的配置传递给ClassPathXmlApplicationContext
         */
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        WebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext(sContextLocation);
        webApplicationContext.setServletContext(servletContext);
        this.context = webApplicationContext;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }
}
