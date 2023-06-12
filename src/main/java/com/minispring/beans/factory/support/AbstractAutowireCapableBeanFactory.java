package com.minispring.beans.factory.support;

import com.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.minispring.beans.factory.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 这里用来保存用户注册的自定义bean processor，类似于解析我们自定义注解，就是一个bean post processor @AutowiredAnnotationBeanPostProcessor
     */
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {
        // 避免重复添加
        this.beanFactoryPostProcessors.remove(beanFactoryPostProcessor);
        this.beanFactoryPostProcessors.add(beanFactoryPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanFactoryPostProcessors.size();
    }

    public List<BeanFactoryPostProcessor> getBeanPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            if (beanFactoryPostProcessor instanceof AutowiredAnnotationBeanFactoryPostProcessor) {
                AutowiredAnnotationBeanFactoryPostProcessor processor = (AutowiredAnnotationBeanFactoryPostProcessor) beanFactoryPostProcessor;
                processor.setBeanFactory(this);
                result = processor.postProcessBeforeInitialization(existingBean, beanName);
                if (result == null)
                    return result;
            }
        }
        return existingBean;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            if (beanFactoryPostProcessor instanceof AutowiredAnnotationBeanFactoryPostProcessor) {
                AutowiredAnnotationBeanFactoryPostProcessor processor = (AutowiredAnnotationBeanFactoryPostProcessor) beanFactoryPostProcessor;
                processor.setBeanFactory(this);
                result = processor.postProcessAfterInitialization(existingBean, beanName);
                if (result == null)
                    return result;
            }
        }
        return existingBean;
    }
}
