package com.minispring.beans.property;

public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
