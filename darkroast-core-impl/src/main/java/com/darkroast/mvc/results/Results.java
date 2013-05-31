package com.darkroast.mvc.results;

/**
 * Static helpers for returning results from an action.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class Results {

    public static ContentResult view(String view) {
        return new RythmTemplate(view);
    }

    public static ContentResult view(String view, Object model) {
        return new RythmTemplate(view).add("model", model);
    }

    public static Result error(int errorCode) {
        return new Error(errorCode);
    }

    public static Result error(int errorCode, String message) {
        return new Error(errorCode, message);
    }

    public static Result redirect(String redirectPath) {
        return new Redirect(redirectPath);
    }
}
