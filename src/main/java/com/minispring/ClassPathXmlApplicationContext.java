package com.minispring;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
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
        SAXReader reader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(xmlFilename);
            Document document = reader.read(xmlPath);
            Element rootElement = document.getRootElement();
            for (Element element : rootElement.elements()) {
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
