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


}
