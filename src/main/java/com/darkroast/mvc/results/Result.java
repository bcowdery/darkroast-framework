package com.darkroast.mvc.results;

import java.io.OutputStream;
import java.util.List;

/**
 * A result that can be returned from a controller action.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public interface Result {

    /**
     * Adds an object to the result for rendering.
     *
     * @param o object to add
     * @return Result with object added (chainable).
     */
    Result add(Object o);

    /**
     * Adds many objects to the result for rendering.
     *
     * @param objects list of objects to add.
     * @return Result with object added (chainable).
     */
    Result add(List<Object> objects);

    /**
     * Render the result to a human readable format.
     *
     * @param contentPath path on disk where template files are stored.
     * @param out output stream to write the rendered result to.
     */
    void render(String contentPath, OutputStream out);
}
