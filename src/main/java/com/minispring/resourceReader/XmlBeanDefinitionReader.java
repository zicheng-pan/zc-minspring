package com.minispring.resourceReader;

import com.minispring.beans.factory.BeanDefinition;
import com.minispring.beans.factory.BeanFactory;
import com.minispring.beans.property.ConstructArgumentValue;
import com.minispring.beans.property.ConstructArgumentValues;
import com.minispring.beans.property.PropertyValue;
import com.minispring.beans.property.PropertyValues;
import com.minispring.beans.resource.Resource;
import org.dom4j.Element;

import java.util.List;


public class XmlBeanDefinitionReader {

    private final BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
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

            for (Element property : propertys) {
                String type = property.attributeValue("type");
                String name = property.attributeValue("name");
                String value = property.attributeValue("value");

                PropertyValue propertyValue = new PropertyValue(name, value, type);
                propertyValues.addPropertyValue(propertyValue);
            }
            beanDefinition.setPropertyValues(propertyValues);

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

            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
