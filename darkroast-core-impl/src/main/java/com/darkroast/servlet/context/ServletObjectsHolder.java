package com.darkroast.servlet.context;

import com.darkroast.servlet.annotations.Destroyed;
import com.darkroast.servlet.annotations.Initialized;
import com.darkroast.servlet.events.ServletContextEvent;
import com.darkroast.servlet.events.ServletRequestEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

/**
 * Holder object for the {@link ServletContext} object.
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
@ApplicationScoped
public class ServletObjectsHolder {

    private final ThreadLocal<ServletRequestContext> requestContext = new ThreadLocal<ServletRequestContext>() {
        @Override
        protected ServletRequestContext initialValue() {
            return null;
        }
    };

    private ServletContext servletContext;


    public ServletObjectsHolder() {
    }


    public ServletRequestContext getRequestContext() {
        return requestContext.get();
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    protected void applicationStartup(@Observes @Initialized ServletContextEvent e) {
        servletContext = e.getServletContext();
    }

    protected void applicationShutdown(@Observes @Destroyed ServletContextEvent e) {
        servletContext = null;
    }

    protected void requestInitialized(@Observes @Initialized ServletRequestEvent e) {
        requestContext.set(e.getRequestContext());
    }

    protected void requestDestroyed(@Observes @Destroyed ServletRequestEvent e) {
        requestContext.remove();
    }
}
