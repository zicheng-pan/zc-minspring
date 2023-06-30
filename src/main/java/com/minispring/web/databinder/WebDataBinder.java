package com.minispring.web.databinder;

import com.minispring.beans.factory.config.property.PropertyValues;
import com.minispring.web.databinder.datatype.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebDataBinder {

    // 用来绑定的对象
    private Object target;
    private Class<?> clz;
    private String objectName;

    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    //核心绑定方法，将request里面的参数值绑定到目标对象的属性上
    public void bind(HttpServletRequest request) {
        // 把 Request 里的参数解析成 PropertyValues。
        PropertyValues mpvs = assignParameters(request);
        // 把 Request 里的参数值添加到绑定参数中。
        addBindValues(mpvs, request);
        // 把两者绑定在一起。
        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);
    }

    //实际将参数值与对象属性进行绑定的方法
    protected void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }


    //将Request参数解析成PropertyValues
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }

    //设置属性值的工具
    protected AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }
    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {
        // 添加一些特殊情况下的request的值对应到特殊类的PropertyValues中来

    }
}
