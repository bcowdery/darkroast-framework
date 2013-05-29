package com.darkroast.dispatch.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A holder object for the current HTTP request cycle.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class HttpContext {

    private static final ThreadLocal<HttpContext> CONTEXT = new ThreadLocal<>();

    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;

    public HttpContext(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;

        CONTEXT.set(this);
    }

    public static HttpContext getInstance() {
        return CONTEXT.get();

    }

    public static HttpServletRequest getHttpRequest() {
        HttpContext context = getInstance();
        return context != null ? context.httpRequest : null;
    }

    public static HttpServletResponse getHttpResponse() {
        HttpContext context = getInstance();
        return context != null ? context.httpResponse : null;
    }
}
