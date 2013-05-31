package com.darkroast.servlet;

import com.darkroast.cdi.BeanManagerUtils;
import com.darkroast.dispatch.Dispatcher;
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
 * DarkroastContextFilter
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastContextFilter implements Filter {

    @Inject Dispatcher dispatcher;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        BeanManager beanManager = BeanManagerUtils.getBeanManager();
        ServletRequestContext requestContext = new ServletRequestContext(request, response);

        beanManager.fireEvent(new ServletRequestEvent(requestContext), InitializedLiteral.INSTANCE);

        try {
            if (!response.isCommitted()) {
                System.out.println(dispatcher);
                chain.doFilter(request, response);
            }
        } finally {
            beanManager.fireEvent(new ServletRequestEvent(requestContext), DestroyedLiteral.INSTANCE);
        }
    }

    @Override
    public void destroy() {
    }
}
