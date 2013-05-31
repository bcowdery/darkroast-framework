package com.darkroast.servlet.context;

import com.darkroast.servlet.annotations.Destroyed;
import com.darkroast.servlet.annotations.Initialized;
import com.darkroast.servlet.events.ServerContextEvent;
import com.darkroast.servlet.events.ServletRequestEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

/**
 * ServletObjectsHolder
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

    protected void applicationStartup(@Observes @Initialized ServerContextEvent e) {
        servletContext = e.getServletContext();
    }

    protected void applicationShutdown(@Observes @Destroyed ServerContextEvent e) {
        servletContext = null;
    }

    protected void requestInitialized(@Observes @Initialized ServletRequestEvent e) {
        requestContext.set(e.getRequestContext());
    }

    protected void requestDestroyed(@Observes @Destroyed ServletRequestEvent e) {
        requestContext.remove();
    }
}
