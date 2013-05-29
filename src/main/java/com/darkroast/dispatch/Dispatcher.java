package com.darkroast.dispatch;

import com.darkroast.mvc.Controller;
import com.darkroast.mvc.Route;
import com.darkroast.mvc.annotations.Current;
import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.annotations.PathLiteral;
import com.darkroast.mvc.results.Result;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.OutputStream;
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

    @Inject @Current Route route;
    @Inject @Any Instance<Controller> controllers;

    public Dispatcher() {
    }

    public void dispatch(ServletContext servletContext, OutputStream out) {
        Annotation path = new PathLiteral(route.getController());
        Controller controller = controllers.select(path).get();

        Method action = getActionMethod(controller, route);
        Result result = null;
        try {
            result = (Result) action.invoke(controller);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access action method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Action method does not exist on class!");
        }

        if (result != null) {
            result.render(servletContext.getRealPath("/content"), out);
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
