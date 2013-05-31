package com.darkroast.servlet;

import com.darkroast.cdi.BeanManagerUtils;
import com.darkroast.config.Application;
import com.darkroast.servlet.annotations.DestroyedLiteral;
import com.darkroast.servlet.annotations.InitializedLiteral;
import com.darkroast.servlet.events.BootstrapEvent;
import com.darkroast.servlet.events.ServerContextEvent;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * DarkroastServletInitilizer
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(DarkroastContextListener.class.getName());

    @Inject Application application;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        loadApplicationProperties();
        logStartup();

        BeanManager beanManager = BeanManagerUtils.getBeanManager();
        beanManager.fireEvent(new ServerContextEvent(servletContext), InitializedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), InitializedLiteral.INSTANCE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        BeanManager beanManager = BeanManagerUtils.getBeanManager();
        beanManager.fireEvent(new ServerContextEvent(servletContext), DestroyedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), DestroyedLiteral.INSTANCE);
    }


    public void loadApplicationProperties() {
        // todo: load darkroast-default.properties from implementation jar
        // todo: look for a darkroast.properties file on the webapp classpath, merge with loaded properties
        // todo: setup the application object and config file properties.
    }

    public void logStartup() {
        LOG.info("\n"
                + "  ____             _    ____                 _    \n"
                + " |  _ \\  __ _ _ __| | _|  _ \\ ___   __ _ ___| |_ \n"
                + " | | | |/ _` | '__| |/ / |_) / _ \\ / _` / __| __| \n"
                + " | |_| | (_| | |  |   <|  _ < (_) | (_| \\__ \\ |_ \n"
                + " |____/ \\__,_|_|  |_|\\_\\_| \\_\\___/ \\__,_|___/\\__|");
    }
}
