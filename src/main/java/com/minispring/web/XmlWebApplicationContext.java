package com.minispring.web;

import com.minispring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

public class XmlWebApplicationContext
        extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}