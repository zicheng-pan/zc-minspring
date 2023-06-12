package com.minispring.web;

import com.minispring.core.ClassPathXmlResource;
import com.minispring.core.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DispatcherSevlet extends HttpServlet {
    String sContextConfigLocation;

    private Map<String, MappingValue> mappingValues;
    private Map<String, Class<?>> mappingClz = new HashMap<>();
    private Map<String, Object> mappingObjs = new HashMap<>();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(rs);
        Refresh();
    }

    private void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object result = null;

        String sPath = req.getServletPath(); //获取请求的path
        MappingValue mappingValue = this.mappingValues.getOrDefault(sPath, null);
        if (mappingValue == null) {
            throw new ServletException("cannot find url handler:" + sPath);
        }
        String method = mappingValue.getMethod();
        Class<?> clazz = mappingClz.get(sPath);
        Object obj = mappingObjs.get(sPath);
        try {
            Method declaredMethod = clazz.getDeclaredMethod(method);
            result = declaredMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().append(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object result = null;

        String sPath = req.getServletPath(); //获取请求的path
        MappingValue mappingValue = this.mappingValues.getOrDefault(sPath, null);
        if (mappingValue == null) {
            throw new ServletException("cannot find url handler:" + sPath);
        }
        String method = mappingValue.getMethod();
        Class<?> clazz = mappingClz.get(sPath);
        Object obj = mappingObjs.get(sPath);
        try {
            Method declaredMethod = clazz.getDeclaredMethod(method);
            result = declaredMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().append(result.toString());
    }
}
