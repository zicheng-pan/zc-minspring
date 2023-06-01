package com.minispring.beans.factory.support;

import com.minispring.beans.factory.interfaces.BeanPostProcessor;
import com.minispring.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 这里用来保存用户注册的自定义bean processor，类似于解析我们自定义注解，就是一个bean post processor @AutowiredAnnotationBeanPostProcessor
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 避免重复添加
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor) {
                AutowiredAnnotationBeanPostProcessor processor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
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
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor) {
                AutowiredAnnotationBeanPostProcessor processor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                processor.setBeanFactory(this);
                result = processor.postProcessAfterInitialization(existingBean, beanName);
                if (result == null)
                    return result;
            }
        }
        return existingBean;
    }
}
