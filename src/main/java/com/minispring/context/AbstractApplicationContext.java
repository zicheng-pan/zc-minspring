package com.minispring.context;

import com.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.minispring.beans.factory.config.ConfigurableListableBeanFactory;
import com.minispring.core.env.Environment;
import com.minispring.context.event.ApplicationEventPublisher;
import com.minispring.beans.factory.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ApplicationContext {
    private Environment environment;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;


    /**
     * 这里实现了，使用接口自动注入ApplicationContext对象
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        Object returnObj = null;
        try {
            returnObj = getBeanFactory().getBean(beanName);
            if (returnObj instanceof ApplicationContextAware) {
                ((ApplicationContextAware) returnObj).setApplicationContext(this);
            }
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
        return returnObj;

    }

    @Override
    public Boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

//	public void registerBean(String beanName, Object obj) {
//		getBeanFactory().registerBean(beanName, obj);
//	}

    @Override
    public Boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public Boolean isPrototype(String name) {
        return getBeanFactory().isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }


    /**
     * 主要是这里注册所有需要的执行的PostProcessor和Listener事件的对象
     *
     * @throws BeansException
     * @throws IllegalStateException
     */
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());

        registerBeanPostProcessors(getBeanFactory());

        initApplicationEventPublisher();

        onRefresh();

        registerListeners();

        finishRefresh();
    }

    public abstract void registerListeners();

    public abstract void initApplicationEventPublisher();

    public abstract void postProcessBeanFactory(ConfigurableListableBeanFactory bf);

    public abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory bf);

    public abstract void onRefresh() throws BeansException;

    public abstract void finishRefresh();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public void addBeanPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanFactoryPostProcessor);

    }

    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }


    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }


    @Override
    public void close() {
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
