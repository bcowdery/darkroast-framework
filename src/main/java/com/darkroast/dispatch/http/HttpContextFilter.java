package com.darkroast.dispatch.http;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple filter that provides ThreadLocal access to the ServletRequest and ServletResponse
 * through the {@link HttpContext} object.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@WebFilter("/*")
public class HttpContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        new HttpContext((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
