package com.darkroast.mvc.results;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Result that returns an HTTP error code and message to the browser.
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public class Error implements Result {

    private int errorCode;
    private String message;

    public Error(int errorCode) {
        this.errorCode = errorCode;
    }

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public void render(HttpServletResponse response, String contentPath) throws IOException {
        if (message != null && !"".equals(message.trim())) {
            response.sendError(errorCode, message);
        } else {
            response.sendError(errorCode);
        }
    }
}
