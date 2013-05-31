package com.darkroast.servlet;

import com.darkroast.config.ApplicationImpl;
import com.darkroast.servlet.annotations.DestroyedLiteral;
import com.darkroast.servlet.annotations.InitializedLiteral;
import com.darkroast.servlet.events.BootstrapEvent;
import com.darkroast.servlet.events.ServerContextEvent;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;
import java.util.logging.Logger;

/**
 * Publishes {@link BootstrapEvent} and {@link ServletContextEvent} events on container startup and
 * applies the {@link DarkroastDispatchFilter} to the configured URL pattern.
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(DarkroastContextListener.class.getName());
    private static final String FILTER_NAME = "Darkroast Dispatch Filter";

    @Inject BeanManager beanManager;
    @Inject
    ApplicationImpl application;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        filterMapping(servletContext);

        beanManager.fireEvent(new ServerContextEvent(servletContext), InitializedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), InitializedLiteral.INSTANCE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        beanManager.fireEvent(new ServerContextEvent(servletContext), DestroyedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), DestroyedLiteral.INSTANCE);
    }

    /**
     * Apply the filter mapping for the {@link DarkroastDispatchFilter} dispatch filter.
     *
     * @param servletContext servlet context
     */
    private void filterMapping(ServletContext servletContext) {
        String urlPattern = application.getConfig().getString("darkroast.dispatch.urlpattern");

        FilterRegistration.Dynamic  filter = servletContext.addFilter(FILTER_NAME, DarkroastDispatchFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, urlPattern);

        LOG.info("\n"
                + "  ____             _    ____                 _    \n"
                + " |  _ \\  __ _ _ __| | _|  _ \\ ___   __ _ ___| |_ \n"
                + " | | | |/ _` | '__| |/ / |_) / _ \\ / _` / __| __| \n"
                + " | |_| | (_| | |  |   <|  _ < (_) | (_| \\__ \\ |_ \n"
                + " |____/ \\__,_|_|  |_|\\_\\_| \\_\\___/ \\__,_|___/\\__|");

        LOG.info("Darkroast " + application.getVersion() + ", " + application.getEnvironment());
        LOG.info("Started on " + servletContext.getContextPath() + urlPattern);
    }
}
