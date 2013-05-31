package com.darkroast.servlet.context;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ServletContext getServletContext() {
        return holder.getServletContext();
    }

    @Produces
    @RequestScoped
    public ServletRequest getServletRequest() {
        return holder.getRequestContext().getServletRequest();
    }


    @Produces
    @RequestScoped
    public ServletResponse getServletResponse() {
        return holder.getRequestContext().getServletResponse();
    }

    @Produces
    @Typed(HttpServletRequest.class)
    @RequestScoped
    public HttpServletRequest getHttpServletRequest() {
        ServletRequest request = holder.getRequestContext().getServletRequest();
        if (request instanceof HttpServletRequest) {
            return HttpServletRequest.class.cast(request);
        }

        throw new IllegalStateException("Request is not an instance of HttpServletRequest.");
    }

    @Produces
    @Typed(HttpServletResponse.class)
    @RequestScoped
    public HttpServletResponse getHttpServletResponse() {
        ServletResponse response = holder.getRequestContext().getServletResponse();
        if (response instanceof HttpServletResponse) {
            return HttpServletResponse.class.cast(response);
        }

        throw new IllegalStateException("Response is not an instance of HttpServletResponse.");
    }
}
