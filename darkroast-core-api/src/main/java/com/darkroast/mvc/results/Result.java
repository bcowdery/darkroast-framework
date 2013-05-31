package com.darkroast.mvc.results;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A result that can be returned from a controller action.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public interface Result {

    /**
     * Render the result to the HTTP response.
     *
     * @param response HTTP response
     * @param contentPath absolute path of the web application context root
     * @throws IOException possible IO exception
     */
    void render(HttpServletResponse response, String contentPath) throws IOException;
}
