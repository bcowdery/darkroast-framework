package com.darkroast.servlet;

import com.darkroast.cdi.BeanManagerUtils;
import com.darkroast.servlet.annotations.InitializedLiteral;
import com.darkroast.servlet.events.ServerContextEvent;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * DarkroastServletInitilizer
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastContextInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {
        BeanManager beanManager = BeanManagerUtils.getBeanManager();
        beanManager.fireEvent(new ServerContextEvent(servletContext), InitializedLiteral.INSTANCE);
    }
}
