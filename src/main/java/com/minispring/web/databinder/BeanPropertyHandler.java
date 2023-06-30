package com.minispring.web.databinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanPropertyHandler {

    Object wrappedObject;
    Method writeMethod = null;
    Method readMethod = null;
    Class<?> propertyClz = null;

    public Class<?> getPropertyClz() {
        return propertyClz;
    }

    public BeanPropertyHandler(Object wrappedObject, String propertyName) {
        this.wrappedObject = wrappedObject;
        this.propertyClz = wrappedObject.getClass();
        Class<?> clz = wrappedObject.getClass();
        try {
            Field field = clz.getDeclaredField(propertyName);
            propertyClz = field.getType();
            this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
            this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Object getValue() {
        Object result = null;
        writeMethod.setAccessible(true);

        try {
            result = readMethod.invoke(wrappedObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;

    }

    public void setValue(Object value) {
        writeMethod.setAccessible(true);
        try {
            writeMethod.invoke(wrappedObject, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
