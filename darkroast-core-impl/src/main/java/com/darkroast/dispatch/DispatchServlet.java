package com.darkroast.dispatch;

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
public class DispatchServlet extends HttpServlet {

    @Inject Dispatcher dispatcher;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dispatcher.dispatch(request, response, getServletContext());
    }
}
