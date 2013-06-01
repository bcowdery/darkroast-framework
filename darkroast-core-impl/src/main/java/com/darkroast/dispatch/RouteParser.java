package com.darkroast.dispatch;

import com.darkroast.config.ApplicationConfig;
import com.darkroast.config.ApplicationConfigImpl;
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
public class RouteParser {

    private static final String ROUTE = "darkroast.application.route";
    private static final String ROUTE_DEFAULT = "^\\/(?<controller>\\w+)\\/?(?<action>\\w*)\\/?(?<id>\\w*)";

    private static Pattern routePattern = null;

    @Inject HttpServletRequest request;
    @Inject ApplicationConfig applicationConfig;

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
            String regex = applicationConfig.getString(ROUTE, ROUTE_DEFAULT);
            routePattern = Pattern.compile(regex);
        }
        return routePattern;
    }
}
