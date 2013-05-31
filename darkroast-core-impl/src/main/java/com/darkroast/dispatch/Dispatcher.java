package com.darkroast.dispatch;

import com.darkroast.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.annotations.PathLiteral;
import com.darkroast.mvc.results.ContentResult;
import com.darkroast.mvc.results.Result;
import com.darkroast.mvc.Route;
import com.darkroast.mvc.results.RythmTemplate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Inject Route route;
    @Inject ServletContext servletContext;
    @Inject @Any Instance<Controller> controllers;

    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        renderResult(response, servletContext.getRealPath("/"), result);
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

    private void renderResult(HttpServletResponse response, String servletPath, Object object) throws IOException {
        if (object == null)
            return;

        Result result;
        if (object instanceof Result) {
            result = (Result) object;
        } else {
            result = new RythmTemplate("@model").add("model", object);
        }

        if (object instanceof ContentResult) {
            String contentType = ((ContentResult) result).getContentType();
            response.setContentType(contentType);
        }

        OutputStream out = null;
        try {
            result.render(response, servletPath);
            out = response.getOutputStream();

        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
