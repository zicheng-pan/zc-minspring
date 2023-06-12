package com.minispring.beans.factory.config;

import com.minispring.beans.factory.exception.BeansException;

import java.util.Map;

/**
 * 将 Factory 内部管理的 Bean 作为一个集合来对待，获取 Bean 的数量，得到所有 Bean 的名字，按照某个类型获取 Bean 列表等等
 *
 *
 * 在BeanFactory的基础上添加对Beandefinition中的内容的操作
 * 1. 可以获取beanDefinition的个数
 * 2. 获取所有的beanDefinition的名称
 * 增强bean的操作
 * 1. 根据class类型来获取Bean的名字
 * 2. 根据class 类型获取所有bean对象
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
