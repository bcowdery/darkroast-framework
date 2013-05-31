package com.darkroast.servlet.context;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.Produces;

/**
 * ServletObjectsProducer
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class ServletObjectsProducer {

    @Inject ServletObjectsHolder holder;

    @Produces
    @ApplicationScoped
    protected ServletContext getServletContext() {
        return holder.getServletContext();
    }

    @Produces
    @RequestScoped
    protected ServletRequest getServletRequest() {
        return holder.getRequestContext().getServletRequest();
    }

    @Produces
    @RequestScoped
    protected ServletResponse getServletResponse() {
        return holder.getRequestContext().getServletResponse();
    }
}
