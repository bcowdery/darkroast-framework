package com.darkroast.servlet;

import com.darkroast.servlet.annotations.DestroyedLiteral;
import com.darkroast.servlet.annotations.InitializedLiteral;
import com.darkroast.servlet.context.ServletRequestContext;
import com.darkroast.servlet.events.ServletRequestEvent;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Filter that fires CDI events for the <code>ServletRequest</code> and <code>ServletResponse</code>
 * lifecycle. This filter must be executed before any other filters to allow CDI injection of
 * the servlet request and response into beans.
 *
 * Filter mapping /*
 *
 * @see DarkroastContextListener
 * @see com.darkroast.servlet.context.ServletObjectsProducer
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
public class DarkroastServletEventFilter implements Filter {

    @Inject BeanManager beanManager;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        ServletRequestContext requestContext = new ServletRequestContext(req, resp);
        beanManager.fireEvent(new ServletRequestEvent(requestContext), InitializedLiteral.INSTANCE);

        try {
            chain.doFilter(req, resp);
        } finally {
            beanManager.fireEvent(new ServletRequestEvent(requestContext), DestroyedLiteral.INSTANCE);
        }
    }

    @Override
    public void destroy() {
    }
}
