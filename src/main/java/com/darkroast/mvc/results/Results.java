package com.darkroast.mvc.results;

/**
 * Static helpers for returning results from an action.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class Results {

    public static Result view(String view) {
        return new RythmTemplate(view);
    }

    public static Result view(String view, Object model) {
        return new RythmTemplate(view).add(model);
    }

    public static Result view(String view, Object model, Object... models) {
        return new RythmTemplate(view).add(model).add(models);
    }
}
