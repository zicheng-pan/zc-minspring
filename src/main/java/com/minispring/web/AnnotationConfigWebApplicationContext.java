package com.minispring.web;

import com.minispring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * 这个类就是在ClassPathXmlApplicationContext类基础上增加了servlet属性
 */
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
