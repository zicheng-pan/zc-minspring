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

    private WebApplicationContext webApplicationContext;

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
//        URL xmlPath = null;
//        try {
//            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
//
//            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

        this.webApplicationContext = new
                AnnotationConfigWebApplicationContext(sContextConfigLocation,
                this.parentApplicationContext);


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
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
    }


    private RequestMappingHandlerMapping handlerMapping;

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    private RequestMappingHandlerAdapter handlerAdapter;

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
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


    /**
     * TODO 将加载class，定义BeanDefinition的步骤抽离出来
     * TODO 将initController分成两个阶段来做，可以避免Controller互相引用出错的问题
     */
    private void initController() {
//        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            /**
             * 直接通过配置来创建Bean，修改为和BeanFacotry结合创建bean
             */
//            Object obj = null;
//            Class<?> clz = null;
//            try {
//                clz = Class.forName(controllerName); //加载类
//                this.controllerClasses.put(controllerName, clz);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                obj = clz.newInstance(); //实例化bean
//                this.controllerObjs.put(controllerName, obj);
//                /**
//                 * 初始化controller中的属性，set Bean
//                 */
//                Field[] declaredFields = clz.getDeclaredFields();
//                for (Field field : declaredFields) {
//                    if (field.isAnnotationPresent(Autowired.class)) {
//                        field.setAccessible(true);
//                        Object bean = this.webApplicationContext.getBean(field.getName());
//                        field.set(obj, bean);
//                    }
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }

//            this.webApplicationContext.registerBeanDefinition(new BeanDefinition(controllerName, controllerName));
//            try {
//                this.controllerObjs.put(controllerName, this.webApplicationContext.getBean(controllerName));
//                this.controllerClasses.put(controllerName, Class.forName(controllerName));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        }
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
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        Object result = null;
//
//        String sPath = req.getServletPath(); //获取请求的path
//        MappingValue mappingValue = this.mappingValues.getOrDefault(sPath, null);
//        if (mappingValue == null) {
//            throw new ServletException("cannot find url handler:" + sPath);
//        }
//        String method = mappingValue.getMethod();
//        Class<?> clazz = mappingClz.get(sPath);
//        Object obj = mappingObjs.get(sPath);
//        try {
//            Method declaredMethod = clazz.getDeclaredMethod(method);
//            result = declaredMethod.invoke(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        resp.getWriter().append(result.toString());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        Object result = null;
//
//        String sPath = req.getServletPath(); //获取请求的path
//        MappingValue mappingValue = this.mappingValues.getOrDefault(sPath, null);
//        if (mappingValue == null) {
//            throw new ServletException("cannot find url handler:" + sPath);
//        }
//        String method = mappingValue.getMethod();
//        Class<?> clazz = mappingClz.get(sPath);
//        Object obj = mappingObjs.get(sPath);
//        try {
//            Method declaredMethod = clazz.getDeclaredMethod(method);
//            result = declaredMethod.invoke(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        resp.getWriter().append(result.toString());
//    }
}
