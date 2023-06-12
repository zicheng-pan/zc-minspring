package com.minispring.web;

import com.minispring.core.Resource;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlConfigReader {

    public Map<String, MappingValue> loadConfig(Resource res) {
        Map<String, MappingValue> map = new HashMap<>();
        while (res.hasNext()) {
            Element element = (Element) res.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String beanMethod = element.attributeValue("value");
            map.put(beanID, new MappingValue(beanID, beanClassName, beanMethod));
        }
        return map;
    }

}
