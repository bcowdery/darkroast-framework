package com.darkroast.model;

import com.darkroast.annotations.ViewModel;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Produces instances of JavaBeans for annotated {@link ViewModel} fields.
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
public class ModelBeanProducer {

    @Inject HttpServletRequest request;

    @Produces
    @Dependent
    @ViewModel
    public Object buildModel(InjectionPoint ip) {
        Class<?> type = resolveExpectedType(ip);
        String prefix = resolvePropertyPath(ip);
        return produceInstance(type, request.getParameterMap(), prefix);
    }

    private Class<?> resolveExpectedType(final InjectionPoint ip) {
        Type t = ip.getType();
        if (t instanceof ParameterizedType && ((ParameterizedType) t).getActualTypeArguments().length == 1) {
            return (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
        } else if (t instanceof Class) {
            return (Class<?>) t;
        } else {
            return Object.class;
        }
    }

    private String resolvePropertyPath(final InjectionPoint ip) {
        String member = ip.getMember().getName();
        return member.startsWith("get") || member.startsWith("set")
                ? member.substring(3, 4).toLowerCase() + member.substring(4) + "."
                : member + ".";
    }

    protected Object produceInstance(Class<?> type, Map<String, String[]> requestParameters, String prefix) {
        Object bean;
        try {
            bean = type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not create new instance.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Class must contain a public constructor.", e);
        }

        Map<String, Object> parameters = subset(requestParameters, prefix);
        try {
            BeanUtils.populate(bean, parameters);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not invoke property accessor method.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Property accessor method does not exist on bean.", e);
        }

        return bean;
    }

    protected Map<String, Object> subset(Map<String, String[]> requestParameters, String prefix) {
        Map<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                parameters.put(entry.getKey().replace(prefix, ""), flatten(entry.getValue()));
            }
        }
        return parameters;
    }

    private Object flatten(String[] value) {
        return value.length == 1 ? value[0] : value;
    }
}
