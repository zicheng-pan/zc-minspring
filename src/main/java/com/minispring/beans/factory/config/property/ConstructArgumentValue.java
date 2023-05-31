package com.minispring.beans.factory.config.property;

public class ConstructArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ConstructArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ConstructArgumentValue(Object value, String type, String name) {
        this(value, type);
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
