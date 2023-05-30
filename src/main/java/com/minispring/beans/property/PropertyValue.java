package com.minispring.beans.property;

public class PropertyValue {

    private final String name;
    private final Object value;

    private final String type;

    public PropertyValue(String name, Object value, String type) {
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }


}
