package com.minispring.beans.property;

public class PropertyValue {

    private final String name;
    private final Object value;

    private final String type;

    private final boolean isRef;

    public PropertyValue(String name, Object value, String type, boolean isRef) {
        this.value = value;
        this.name = name;
        this.type = type;
        this.isRef = isRef;
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

    public boolean isRef() {
        return isRef;
    }
}
