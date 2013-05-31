package com.darkroast.servlet.events;

import javax.servlet.ServletContext;

/**
 * BootstrapEvent
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public class BootstrapEvent {

    private String displayName;
    private String contextPath;

    public BootstrapEvent() {
    }

    public BootstrapEvent(ServletContext servletContext) {
        this.displayName = servletContext.getServletContextName();
        this.contextPath = servletContext.getContextPath();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
