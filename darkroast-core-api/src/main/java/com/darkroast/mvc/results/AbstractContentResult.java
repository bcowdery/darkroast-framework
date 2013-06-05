package com.darkroast.mvc.results;

/**
 * AbstractContentResult
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public abstract class AbstractContentResult implements ContentResult {

    protected String contentType = "text/html";

    public String getContentType() {
        return contentType;
    }

    @Override
    public ContentResult contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
