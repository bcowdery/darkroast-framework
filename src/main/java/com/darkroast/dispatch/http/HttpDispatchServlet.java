package com.darkroast.dispatch.http;

import com.darkroast.dispatch.Dispatcher;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Dispatches requests through the DarkRoast MVC {@link Dispatcher}.
 *
 * @author Brian Cowdery
 * @since 05-29-2013
 */
@WebServlet("/")
public class HttpDispatchServlet extends HttpServlet {

    @Inject Dispatcher dispatcher;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dispatch(getServletContext(), resp.getOutputStream());
    }
}
