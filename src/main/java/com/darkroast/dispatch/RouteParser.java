package com.darkroast.dispatch;

import com.darkroast.config.Application;
import com.darkroast.dispatch.http.HttpContext;
import com.darkroast.mvc.Route;
import com.darkroast.mvc.annotations.Current;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the current request path and determines the {@link Route} for the request
 * to be dispatched to.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class RouteParser {

    private static final String ROUTE = "darkroast.application.route";
    private static final String ROUTE_DEFAULT = "\\/(?<controller>\\w+)\\/(?<action>\\w+)\\/(?<id>\\w+)";

    private static Pattern routePattern = null;

    private Pattern getRoutePattern() {
        if (routePattern == null) {
            String regex = Application.getConfig().getString(ROUTE, ROUTE_DEFAULT);
            routePattern = Pattern.compile(regex);
        }
        return routePattern;
    }

    @Produces
    @Current
    @RequestScoped
    public Route parseRoute() {
        Route route = new Route();

        String path = HttpContext.getHttpRequest().getServletPath();
        Matcher matcher = getRoutePattern().matcher(path);

        if (matcher.matches()) {
            route.setController(matcher.group("controller"));
            route.setAction(matcher.group("action"));
        }

        return route;
    }
}