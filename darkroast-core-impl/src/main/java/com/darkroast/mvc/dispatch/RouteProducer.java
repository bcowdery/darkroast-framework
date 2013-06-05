package com.darkroast.mvc.dispatch;

import com.darkroast.config.ApplicationSettings;
import com.darkroast.mvc.Route;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the current request path and determines the {@link com.darkroast.mvc.Route} for the request
 * to be dispatched to.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class RouteProducer {

    private static Pattern routePattern = null;

    @Inject HttpServletRequest request;
    @Inject ApplicationSettings applicationSettings;

    @Produces
    @RequestScoped
    public Route parseRoute() {
        Route route = new Route();

        Matcher matcher = getRoutePattern().matcher(request.getServletPath());

        if (matcher.matches()) {
            String controller = matcher.group("controller");
            String action = matcher.group("action");

            if (controller != null && !"".equals(controller.trim()))
                route.setController(controller);

            if (action != null && !"".equals(action.trim()))
                route.setAction(action);
        }

        return route;
    }

    public Pattern getRoutePattern() {
        if (routePattern == null) {
            routePattern = Pattern.compile(applicationSettings.getDispatchRouteRegex());
        }
        return routePattern;
    }
}
