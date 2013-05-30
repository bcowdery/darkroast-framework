package com.darkroast.dispatch;

import com.darkroast.config.Application;

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
    private static final String ROUTE_DEFAULT = "^\\/(?<controller>\\w+)\\/?(?<action>\\w*)\\/?(?<id>\\w*)";

    private static Pattern routePattern = null;

    private Pattern getRoutePattern() {
        if (routePattern == null) {
            String regex = Application.getConfig().getString(ROUTE, ROUTE_DEFAULT);
            routePattern = Pattern.compile(regex);
        }
        return routePattern;
    }

    public Route parseRoute(String path) {
        Route route = new Route();

        Matcher matcher = getRoutePattern().matcher(path);

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
}
