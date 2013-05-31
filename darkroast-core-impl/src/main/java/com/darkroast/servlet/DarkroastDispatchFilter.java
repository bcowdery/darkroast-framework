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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DarkroastDispatchFilter
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastDispatchFilter implements Filter {

    @Inject Dispatcher dispatcher;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        BeanManager beanManager = BeanManagerUtils.getBeanManager();
        ServletRequestContext requestContext = new ServletRequestContext(req, resp);
        beanManager.fireEvent(new ServletRequestEvent(requestContext), InitializedLiteral.INSTANCE);

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            if (!response.isCommitted()) {
                dispatcher.dispatch(request, response);
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
