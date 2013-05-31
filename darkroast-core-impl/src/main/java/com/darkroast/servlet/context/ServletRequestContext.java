package com.darkroast.servlet.context;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * ServletRequestContext
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class ServletRequestContext implements Serializable {

    public static long serialVersionUID = 1L;

    private ServletRequest servletRequest;
    private ServletResponse servletResponse;

    public ServletRequestContext(ServletRequest servletRequest, ServletResponse servletResponse) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServletRequestContext that = (ServletRequestContext) o;

        if (!servletRequest.equals(that.servletRequest)) return false;
        if (!servletResponse.equals(that.servletResponse)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = servletRequest.hashCode();
        result = 31 * result + servletResponse.hashCode();
        return result;
    }
}
