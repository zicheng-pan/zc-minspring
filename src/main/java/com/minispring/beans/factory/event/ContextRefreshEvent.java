package com.minispring.beans.factory.event;

public class ContextRefreshEvent extends ApplicationEvent {


    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }


    @Override
    public String toString() {
        return this.msg;
    }
}
