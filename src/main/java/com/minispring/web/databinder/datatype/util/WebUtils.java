package com.minispring.web.databinder.datatype.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class WebUtils {
    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }
        Iterator iterator = request.getParameterNames().asIterator();
        while (iterator.hasNext()) {
            String paramName = iterator.next().toString();
            if (paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String value = request.getParameter(paramName);
                params.put(unprefixed, value);
            }
        }
        return params;
    }
}
