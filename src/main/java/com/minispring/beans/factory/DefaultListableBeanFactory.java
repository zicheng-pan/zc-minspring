package com.minispring.beans.factory;

import com.minispring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.beans.factory.config.ConfigurableListableBeanFactory;
import com.minispring.beans.factory.exception.BeansException;

import java.util.*;

// TODO need to test
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        String[] dependsOn = this.getBeanDefinition(beanName).getDependsOn();
        List<String> list = Arrays.stream(dependsOn).toList();
        list.add(dependentBeanName);
        this.getBeanDefinition(beanName).setDependsOn((String[]) list.toArray());
    }

    /**
     * 获取依赖这个bean的所有bean
     *
     * @param beanName
     * @return
     */
    @Override
    public String[] getDependentBeans(String beanName) {
        List<String> result = new LinkedList<>();
        for (String bean : this.beanDefinitionNames) {
            BeanDefinition beanDefinition = this.getBeanDefinition(bean);
            for (String dependeon : beanDefinition.getDependsOn()) {
                if (dependeon.equals(beanName)) {
                    result.add(bean);
                }
            }
        }
        return (String[]) result.toArray();
    }

    /**
     * 获取这个bean依赖了的所有bean
     *
     * @param beanName
     * @return
     */
    @Override
    public String[] getDependenciesForBean(String beanName) {
        return this.getBeanDefinition(beanName).getDependsOn();
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }


    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                matchFound = true;
            } else {
                matchFound = false;
            }
            if (matchFound) {
                result.add(beanName);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new LinkedHashMap<>();
        String[] beanNames = this.getBeanNamesForType(type);
        for (String beanName : beanNames) {
            Object instance = this.getBean(beanName);
            result.put(beanName, (T) instance);
        }
        return result;
    }
}
