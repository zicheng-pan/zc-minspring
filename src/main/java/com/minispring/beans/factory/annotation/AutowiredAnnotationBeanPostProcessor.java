package com.minispring.beans.factory.annotation;

import com.minispring.beans.factory.BeanPostProcessor;
import com.minispring.exception.BeansException;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    public AutowiredAnnotationBeanPostProcessor(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        for (Field declaredField : clazz.getDeclaredFields()) {
            // 解析这个bean的类中的每个属性是否有@Autowired注解
            boolean isAutowired = declaredField.isAnnotationPresent(Autowired.class);
            if (isAutowired) {
                String filedName = declaredField.getName();
                /**
                 * 这里支持使用属性名来依赖查找注入bean，TODO 可以增强对Autowired注解value属性的解析进行依赖查找
                 */
                Object autowiredObject = this.beanFactory.getBean(filedName);
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, autowiredObject);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
