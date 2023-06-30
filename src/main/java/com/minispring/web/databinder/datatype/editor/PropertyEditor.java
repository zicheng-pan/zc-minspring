package com.minispring.web.databinder.datatype.editor;

public interface PropertyEditor {
    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    String getAsText();
}
