package com.darkroast.servlet.events;

import javax.servlet.ServletContext;

/**
 * ServletContextEvent
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class ServletContextEvent {

    private ServletContext servletContext;

    public ServletContextEvent() {
    }

    public ServletContextEvent(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
