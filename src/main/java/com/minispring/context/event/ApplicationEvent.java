package com.minispring.context.event;

import java.util.EventObject;

/*
    借助于jdk的事件机制，进行Event类型的封装
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    protected String msg = null;

    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg = arg0.toString();
    }
}
