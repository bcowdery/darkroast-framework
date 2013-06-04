package com.darkroast.model;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Produces instances of JavaBeans for annotated {@link Model} fields.
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
public class ModelBeanProducer {

    @Produces
    @Dependent
    @Model
    public Object resolveViewModel(InjectionPoint ip) {
        Class<?> type = resolveExpectedType(ip);
        Object o;
        try {
            o = type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not create new instance.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not create new instance.");
        }

        return o;
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
}
