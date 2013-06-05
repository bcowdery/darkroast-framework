package com.darkroast.servlet;

import com.darkroast.config.ApplicationSettings;
import com.darkroast.servlet.annotations.DestroyedLiteral;
import com.darkroast.servlet.annotations.InitializedLiteral;
import com.darkroast.servlet.events.BootstrapEvent;
import com.darkroast.servlet.events.ServletContextEvent;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;
import java.util.logging.Logger;

/**
 * Publishes {@link BootstrapEvent} and {@link javax.servlet.ServletContextEvent} events on container startup and
 * applies the {@link DarkroastServletEventFilter} and {@link DarkroastDispatchFilter} servlet filters
 * to the configured URL patterns.
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(DarkroastContextListener.class.getName());

    private static final String DISPATCH_FILTER = "Darkroast Dispatch Filter";
    private static final String EVENT_FILTER = "Darkroast Servlet Event Filter";

    private static final EnumSet<DispatcherType> DISPATCHER_TYPES = EnumSet.of(
            DispatcherType.REQUEST,
            DispatcherType.FORWARD,
            DispatcherType.ASYNC
    );


    @Inject BeanManager beanManager;
    @Inject ApplicationSettings applicationSettings;


    @Override
    public void contextInitialized(javax.servlet.ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        addFilterMappings(servletContext);

        beanManager.fireEvent(new ServletContextEvent(servletContext), InitializedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), InitializedLiteral.INSTANCE);
    }

    @Override
    public void contextDestroyed(javax.servlet.ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        beanManager.fireEvent(new ServletContextEvent(servletContext), DestroyedLiteral.INSTANCE);
        beanManager.fireEvent(new BootstrapEvent(servletContext), DestroyedLiteral.INSTANCE);
    }

    /**
     * Apply the filter mapping for the {@link DarkroastServletEventFilter} and
     * {@link DarkroastDispatchFilter} servlet filters.
     *
     * @param servletContext servlet context
     */
    protected void addFilterMappings(ServletContext servletContext) {
        FilterRegistration.Dynamic requestFilter = servletContext.addFilter(EVENT_FILTER, DarkroastServletEventFilter.class);
        requestFilter.addMappingForUrlPatterns(DISPATCHER_TYPES, true, "/*");

        String urlPattern = applicationSettings.getDispatchUrlPattern();

        FilterRegistration.Dynamic dispatchFilter = servletContext.addFilter(DISPATCH_FILTER, DarkroastDispatchFilter.class);
        dispatchFilter.addMappingForUrlPatterns(DISPATCHER_TYPES, true, urlPattern);

        LOG.info("\n"
                + "  ____             _    ____                 _    \n"
                + " |  _ \\  __ _ _ __| | _|  _ \\ ___   __ _ ___| |_ \n"
                + " | | | |/ _` | '__| |/ / |_) / _ \\ / _` / __| __| \n"
                + " | |_| | (_| | |  |   <|  _ < (_) | (_| \\__ \\ |_ \n"
                + " |____/ \\__,_|_|  |_|\\_\\_| \\_\\___/ \\__,_|___/\\__|");

        LOG.info("Darkroast " + applicationSettings.getVersion() + ", " + applicationSettings.getEnvironment());
        LOG.info("Started on " + servletContext.getContextPath() + urlPattern);
    }
}
