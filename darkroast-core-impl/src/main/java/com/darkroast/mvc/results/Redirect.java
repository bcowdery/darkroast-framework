package com.darkroast.mvc.results;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirect
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public class Redirect implements Result {

    private String redirectPath;


    public Redirect(String redirectPath) {
        this.redirectPath = redirectPath;
    }


    @Override
    public void render(HttpServletResponse response, String contentPath) throws IOException {
        response.sendRedirect(redirectPath);
    }
}
