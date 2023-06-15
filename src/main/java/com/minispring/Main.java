package com.minispring;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {

//        1. 在启动 Web 项目时，Tomcat 会读取 web.xml 中的 comtext-param 节点，获取这个 Web 应用的全局参数。
//        2. Tomcat 创建一个 ServletContext 实例，是全局有效的。
//        3. 将 context-param 的参数转换为键值对，存储在 ServletContext 里。
//        4. 创建 listener 中定义的监听类的实例，按照规定 Listener 要继承自 ServletContextListener。监听器初始化方法是 contextInitialized(ServletContextEvent event)。初始化方法中可以通过 event.getServletContext().getInitParameter(“name”) 方法获得上下文环境中的键值 对。
//        5. 当 Tomcat 完成启动，也就是 contextInitialized 方法完成后，再对 Filter 过滤器进行初始 化。
//        6. servlet 初始化:有一个参数 load-on-startup，它为正数的值越小优先级越高，会自动启 动，如果为负数或未指定这个参数，会在 servlet 被调用时再进行初始化。init-param 是 一个 servlet 整个范围之内有效的参数，在 servlet 类的 init() 方法中通过 this.getInitParameter(′′param1′′) 方法获得。

        // 启动 tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();
        // 创建 WebApp
        Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes",
                        new File("target/classes").getAbsolutePath(), "/"));
        context.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();

    }
}
