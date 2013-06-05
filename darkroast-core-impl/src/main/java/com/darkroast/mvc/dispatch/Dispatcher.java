package com.darkroast.mvc.dispatch;

import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.annotations.PathLiteral;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.Route;
import com.darkroast.mvc.results.Result;
import com.darkroast.mvc.results.RythmTemplate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Dispatches requests to the mapped {@link Controller} action and produces an injectable
 * {@link Result} from the executed action method.
 *
 * A controller action can return any object that can be converted to a string, but ideally
 * action methods should return Result implementations.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class Dispatcher {

    @Inject Route route;
    @Inject @Any Instance<Controller> controllers;

    @Produces
    @RequestScoped
    public Result invokeAction() {
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

        if (result instanceof Result) {
            return Result.class.cast(result);
        } else {
            return new RythmTemplate("@model").add("model", result);
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
