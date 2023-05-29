package com.minispring;

import com.minispring.resource.ClassPathXmlResource;
import com.minispring.resource.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private Map<String, Object> singletons = new HashMap<>();


    public ClassPathXmlApplicationContext(String xmlFilename) {
        readXML(xmlFilename);
        instanceBeans();
    }

    /**
     * init Spring Bean definition by Constructor
     *
     * @param xmlFilename xml file in resouces path
     */
    private void readXML(String xmlFilename) {
        Resource xmlResource = new ClassPathXmlResource(xmlFilename);
        while (xmlResource.hasNext()) {
            Element element = (Element) xmlResource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanDefinitions.add(beanDefinition);
        }
    }

    /**
     * init object form beanDefinition list and store in singletonMap
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : this.beanDefinitions) {
            try {
                //  clazz.getDeclaredConstructor().newInstance()
                singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object getBean(String beanName) {
        return singletons.getOrDefault(beanName, null);
    }


}
