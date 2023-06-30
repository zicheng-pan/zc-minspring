package com.minispring.web.databinder;

import com.minispring.beans.factory.config.property.PropertyValue;
import com.minispring.beans.factory.config.property.PropertyValues;

public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport{
    PropertyValues propertyValues;

    public AbstractPropertyAccessor(){
        super();
    }

    public void setPropertyValues(PropertyValues pvs) {
        this.propertyValues = pvs;
        for (PropertyValue pv : this.propertyValues.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    public abstract void setPropertyValue(PropertyValue pv);
}
