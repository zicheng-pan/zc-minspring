package com.minispring.beans.factory.support;

import com.minispring.beans.factory.BeanFactory;
import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.beans.factory.config.property.ConstructArgumentValue;
import com.minispring.beans.factory.config.property.ConstructArgumentValues;
import com.minispring.beans.factory.config.property.PropertyValue;
import com.minispring.beans.factory.config.property.PropertyValues;
import com.minispring.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * @param beanName 对应 beanDefinition 对应 具体的bean object
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // Get bean if exists.
        Object singleton = this.getSinglenton(beanName);
        // If bean doesn't exist init it.
        if (singleton == null) {
            // 如果没有初始化好的对象，那么去earlySingletonObjects这里查找，用来解决循环依赖问题
            singleton = this.earlySingletonObjects.getOrDefault(beanName, null);
            // 如果连毛胚都没有，那么进行创建bean
            if (singleton == null) {
                int index = beanNames.indexOf(beanName);
                if (index != -1) {
                    // Init bean
                    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                    try {
                        singleton = createBean(beanDefinition);
                        this.registerSinglenton(beanName, singleton);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new BeansException("class not defined!");
                }
            }
        }
        return singleton;
    }

    /**
     * 根据beanDefinition来创建对象
     *
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Object obj = null;
        try {
            obj = doCreateBean(beanDefinition);
            this.earlySingletonObjects.put(beanDefinition.getId(), obj);
            handleProperties(beanDefinition, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> constructor = null;

        clz = Class.forName(beanDefinition.getClassName());
        ConstructArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
        // 如果xml配置文件中对这个beans，配置了构造参数
        if (!constructorArgumentValues.isEmpty()) {
            Class<?>[] paramTypes = new Class[constructorArgumentValues.getArgumentCount()];
            List<Object> paramValues = new ArrayList<>();

            for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                ConstructArgumentValue indexedArgument = constructorArgumentValues.getArgumentValueList().get(i);
                if ("String".equals(indexedArgument.getType()) || "java.lang.String".equals(indexedArgument.getType())) {
                    paramTypes[i] = String.class;
                    paramValues.add(String.valueOf(indexedArgument.getValue()));
                } else if ("Integer".equals(indexedArgument.getType()) || "java.lang.Integer".equals(indexedArgument.getType())) {
                    paramTypes[i] = Integer.class;
                    paramValues.add(Integer.valueOf(String.valueOf(indexedArgument.getValue())));
                } else if ("int".equals(indexedArgument.getType())) {
                    paramTypes[i] = int.class;
                    paramValues.add(Integer.valueOf(String.valueOf(indexedArgument.getValue())));
                } else {
                    paramTypes[i] = String.class;
                    paramValues.add(String.valueOf(indexedArgument.getValue()));
                }
            }

            // 按照指定构造器创建实例
            constructor = clz.getDeclaredConstructor(paramTypes);
            // 根据参数来创建对象
            obj = constructor.newInstance(paramValues.toArray());
        } else {
            // 没有配置构造参数，通过默认构造器直接创建对象
            obj = clz.getDeclaredConstructor().newInstance();
        }
        return obj;
    }

    private void handleProperties(BeanDefinition beanDefinition, Object obj) throws Exception {

        Class clz = Class.forName(beanDefinition.getClassName());
        // 有了对象后，根据xml文件的Property配置，创建对象属性
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {

                Object[] paramValues = new Object[1];
                //对每一个属性，分数据类型分别处理
                PropertyValue property =
                        propertyValues.getPropertyValueList().get(i);
                String type = property.getType();
                String name = property.getName();
                Object value = property.getValue();
                Class<?>[] paramTypes = new Class<?>[1];
                if (!property.isRef()) {
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(type) ||
                            "java.lang.Integer".equals(type)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(type)) {
                        paramTypes[0] = int.class;
                    } else { // 默认为string
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = value;
                } else {
                    paramTypes[0] = Class.forName(type);
                    paramValues[0] = getBean((String) value);
                }

                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + name.substring(0, 1).toUpperCase()
                        + name.substring(1);
                Method method = null;
                method = clz.getMethod(methodName, paramTypes);
                method.invoke(obj, paramValues);
            }
        }
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanNames.add(beanDefinition.getId());
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
        this.beanDefinitionNames.add(beanDefinition.getId());
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSinglenton(beanName, obj);
    }

    @Override
    public Boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public Boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isProtoType();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getBeanClass().getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanNames.add(name);
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        /**
         * lazy-init bean
         */

        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    public void refresh() throws BeansException {
        for (String beanName : beanDefinitionNames) {
            getBean(beanName);
        }
    }
}
