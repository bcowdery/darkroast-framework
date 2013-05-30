package com.darkroast.dispatch;

import com.darkroast.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.literals.PathLiteral;
import com.darkroast.mvc.Result;
import com.darkroast.mvc.Route;
import com.darkroast.results.RythmTemplate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Dispatches requests to the mapped {@link Controller}.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@Named
@RequestScoped
public class Dispatcher {

    @Inject @Any Instance<Controller> controllers;

    public Dispatcher() {
    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
        Route route = new RouteParser().parseRoute(request.getServletPath());

        Annotation path = new PathLiteral(route.getController());
        Controller controller = controllers.select(path).get();

        // try and invoke action
        Method action = getActionMethod(controller, route);
        Object result;
        try {
            result = action.invoke(controller);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access action method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Action method does not exist on class!");
        }


        // render result
        if (result != null) {
            Result out = result instanceof Result
                    ? (Result) result
                    : new RythmTemplate("@result").param("result", result);

            try {
                out.render(servletContext.getRealPath("/"), response.getOutputStream());

            } catch (IOException e) {
                throw new RuntimeException("Error rendering result", e);
            }
        }
    }

    private Method getActionMethod(Controller controller, Route route) {
        for (Method method : controller.getClass().getMethods()) {
            Path path = method.getAnnotation(Path.class);

            if (path != null && route.getAction().equals(path.value())) {
                return method;
            }

            if (route.getAction().equals(method.getName())) {
                return method;
            }
        }

        throw new RuntimeException("Action not found!");
    }
}
