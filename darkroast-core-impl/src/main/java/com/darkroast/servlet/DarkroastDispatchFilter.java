package com.darkroast.servlet;

import com.darkroast.mvc.results.ContentResult;
import com.darkroast.mvc.results.Result;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Renders the result of a dispatched action to the servlet response output stream.
 *
 * @see com.darkroast.dispatch.Dispatcher
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DarkroastDispatchFilter implements Filter {

    @Inject Result result;
    @Inject ServletContext servletContext;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;

        if (!response.isCommitted()) {
            renderResult(response, servletContext.getRealPath("/"), result);
        }

        if (!response.isCommitted()) {
            chain.doFilter(req, resp);
        }
    }

    protected void renderResult(HttpServletResponse response, String servletPath, Result result) throws IOException {
        if (result instanceof ContentResult) {
            String contentType = ((ContentResult) result).getContentType();
            response.setContentType(contentType);
        }

        OutputStream out = null;
        try {
            result.render(response, servletPath);
            out = response.getOutputStream();

        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    @Override
    public void destroy() {
    }
}
