package com.darkroast.servlet.events;

import javax.servlet.ServletContext;

/**
 * ServerContextEvent
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class ServerContextEvent {

    private ServletContext servletContext;

    public ServerContextEvent() {
    }

    public ServerContextEvent(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
