package com.minispring.beans.factory.xml;

import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.beans.factory.config.BeanFactory;
import com.minispring.beans.factory.config.property.ConstructArgumentValue;
import com.minispring.beans.factory.config.property.ConstructArgumentValues;
import com.minispring.beans.factory.config.property.PropertyValue;
import com.minispring.beans.factory.config.property.PropertyValues;
import com.minispring.beans.factory.support.AbstractBeanFactory;
import com.minispring.beans.factory.support.SimpleBeanFactory;
import com.minispring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;


public class XmlBeanDefinitionReader {

    private final AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();

            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            // 获取属性标签，生成PropertyValue对象，并放在beansDefinition中
            List<Element> propertys = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();

            // 记录所有的依赖引用对象
            List<String> refs = new ArrayList<>();
            for (Element property : propertys) {
                String type = property.attributeValue("type");
                String name = property.attributeValue("name");
                String value = property.attributeValue("value");
                String ref = property.attributeValue("ref");
                boolean isRef = false;
                if (value != null && !value.equals("")) {
                    isRef = false;
                } else if (ref != null && !ref.equals("")) {
                    isRef = true;
                    value = ref;
                    refs.add(ref);
                }
                PropertyValue propertyValue = new PropertyValue(name, value, type, isRef);
                propertyValues.addPropertyValue(propertyValue);
            }
            beanDefinition.setPropertyValues(propertyValues);

            beanDefinition.setDependsOn(refs.toArray(new String[refs.size()]));
            // 同理生成构造器的对象放在beanDefinition中
            List<Element> constructors = element.elements("constructor-arg");
            ConstructArgumentValues constructArgumentValues = new ConstructArgumentValues();

            for (Element arguments : constructors) {
                String type = arguments.attributeValue("type");
                String name = arguments.attributeValue("name");
                String value = arguments.attributeValue("value");

                ConstructArgumentValue constructArgumentValue = new ConstructArgumentValue(value, type, name);
                constructArgumentValues.addGenericArgumentValue(constructArgumentValue);
            }
            beanDefinition.setConstructorArgumentValues(constructArgumentValues);

            this.beanFactory.registerBeanDefinition(beanDefinition.getId(), beanDefinition);
        }
    }
}
