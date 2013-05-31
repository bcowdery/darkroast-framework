package com.darkroast.mvc.results;

import java.util.Map;

/**
 * ContentResult
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public interface ContentResult extends Result {

    public String getContentType();


    /**
     * Sets the HTTP content type for the rendered response
     *
     * @param contentType content MIME type
     * @return Chainable result object
     */
    Result contentType(String contentType);

    /**
     * Adds an object to the result for rendering.
     *
     * @param key key
     * @param object object to add
     * @return Chainable result object
     */
    Result add(String key, Object object);

    /**
     * Adds many objects to the result for rendering.
     *
     * @param objects list of objects to add.
     * @return Chainable result object
     */
    Result add(Map<String, Object> objects);
}
