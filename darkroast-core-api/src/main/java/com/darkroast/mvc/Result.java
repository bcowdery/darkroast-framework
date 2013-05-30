package com.darkroast.mvc;

import java.io.OutputStream;
import java.util.Map;

/**
 * A result that can be returned from a controller action.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public interface Result {

    /**
     * Adds a parameter to the result for rendering.
     *
     * @param key key
     * @param object object to add
     * @return Result with object added (chainable).
     */
    Result param(String key, Object object);

    /**
     * Adds many parameters to the result for rendering.
     *
     * @param objects list of objects to add.
     * @return Result with object added (chainable).
     */
    Result params(Map<String, Object> objects);

    /**
     * Render the result to a human readable format.
     *
     * @param contentPath path on disk where template files are stored.
     * @param out output stream to write the rendered result to.
     */
    void render(String contentPath, OutputStream out);
}