package com.minispring.web;

import com.minispring.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherSevlet extends HttpServlet {
    String sContextConfigLocation;

    private Map<String, MappingValue> mappingValues;

    // 保存自定义的@RequestMapping名称(URL的名称)的列表
    private List<String> urlMappingNames = new ArrayList<>();

    // 保存URL名称与对象的映射关系
    private Map<String, Class<?>> mappingClz = new HashMap<>();

    // 保存URL名称与方法的映射关系
    private Map<String, Object> mappingObjs = new HashMap<>();

    // 需要扫描的package列表
    private List<String> packageNames = new ArrayList<>();

    // controller名称与对象的映射关系
    private Map<String, Object> controllerObjs = new HashMap<>();

    // controller名称
    private List<String> controllerNames = new ArrayList<>();

    // controller名称与类的映射关系
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    WebApplicationContext webApplicationContext;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.webApplicationContext = (WebApplicationContext)
                this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);

            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /**
         * 使用在自定义servlet.xml配置beans来做解析的方式
         */
//        Resource rs = new ClassPathXmlResource(xmlPath);
//        XmlConfigReader reader = new XmlConfigReader();
//        mappingValues = reader.loadConfig(rs);
        refresh();
    }

    protected void refresh() {
        /**
         * 解析bean标签
         */
//        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
//            String id = entry.getKey();
//            String className = entry.getValue().getClz();
//            Object obj = null;
//            Class<?> clz = null;
//            try {
//                clz = Class.forName(className);
//                obj = clz.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            mappingClz.put(id, clz);
//            mappingObjs.put(id, obj);
//        }
        initController();
        initMapping();
    }

    private void initMapping() {
        this.mappingValues = new HashMap<>();
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object controller = this.controllerObjs.get(controllerName);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String urlValue = method.getAnnotation(RequestMapping.class).value();
                    String methodName = method.getName();
                    MappingValue mappingValue = new MappingValue(urlValue, controllerName, methodName);
                    this.mappingValues.put(urlValue, mappingValue);
                    this.mappingClz.put(urlValue, clazz);
                    this.mappingObjs.put(urlValue, controller);
                    this.urlMappingNames.add(urlValue);
                }
            }
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> controllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            controllerNames.addAll(this.scanPackage(packageName));
        }
        return controllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        //将以.分隔的包名换成以/分隔的uri
        try {
            uri = this.getClass().getResource("/" +
                    packageName.replaceAll("\\.", "/")).toURI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        File dir = new File(uri); //处理对应的文件目录
        for (File file : dir.listFiles()) { //目录下的文件或者子目录 if(file.isDirectory()){ //对子目录递归扫描
            if (file.isDirectory()) { //对子目录递归扫描
                scanPackage(packageName + "." + file.getName());
            } else { //类文件
                String controllerName = packageName + "."
                        + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }


    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName); //加载类
                this.controllerClasses.put(controllerName, clz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance(); //实例化bean
                this.controllerObjs.put(controllerName, obj);
                /**
                 * 初始化controller中的属性，set Bean
                 */
                Field[] declaredFields = clz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        field.setAccessible(true);
                        Object bean = this.webApplicationContext.getBean(field.getName());
                        field.set(obj, bean);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
