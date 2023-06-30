package com.minispring.web.databinder;

import com.minispring.beans.factory.config.property.PropertyValue;
import com.minispring.web.databinder.datatype.editor.PropertyEditor;


public class BeanWrapperImpl extends AbstractPropertyAccessor {
    Object wrappedObject;
    Class<?> clz;


    public BeanWrapperImpl(Object object) {
        super();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }


    @Override
    public void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(this.wrappedObject, pv.getName());
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (pe == null) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        if (pe != null) {
            pe.setAsText((String) pv.getValue());
            propertyHandler.setValue(pe.getValue());
        } else {
            propertyHandler.setValue(pv.getValue());
        }
    }

}
