package com.darkroast.servlet.events;

import com.darkroast.servlet.context.ServletRequestContext;

/**
 * Event object for servlet request initialization and destruction events.
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class ServletRequestEvent {

    private ServletRequestContext requestContext;

    public ServletRequestEvent() {
    }

    public ServletRequestEvent(ServletRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public ServletRequestContext getRequestContext() {
        return requestContext;
    }
}
