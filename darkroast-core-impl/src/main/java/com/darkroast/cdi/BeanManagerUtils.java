/*
 * Doula Company Client Manager
 * Copyright (c) 2012 Brian Cowdery
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 */

package com.darkroast.cdi;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Utilities for working with CDI managed beans and the BeanManager.
 *
 * @author Brian Cowdery
 * @since 11-Oct-2012
 */
public class BeanManagerUtils {

    private static final ThreadLocal<BeanManager> MANAGER = new ThreadLocal<BeanManager>() {
        @Override
        protected BeanManager initialValue() {
            return BeanManagerLookup.findBeanManager();
        }
    };

    private BeanManagerUtils() {}

    /**
     * Returns the underlying CDI BeanManager instance.
     *
     * @return BeanManager instance.
     */
    public static BeanManager getBeanManager() {
        return MANAGER.get();
    }

    /**
     * Retrieves a reference to a CDI managed bean.
     *
     * @param clazz class of type T to lookup from the CDI BeanManager
     * @param classifiers classifier annotations for lookup
     * @param <T> type of bean to lookup from the CDI BeanManager
     * @return bean reference, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz, Annotation... classifiers) {
        BeanManager bm = getBeanManager();

        Set<Bean<?>> beans = bm.getBeans(clazz, classifiers);
        if (beans.isEmpty()) return null;

        Bean<T> bean = (Bean<T>) bm.getBeans(clazz, classifiers).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean);
        return (T) bm.getReference(bean, clazz, ctx);
    }
}
