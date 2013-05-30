package com.darkroast.results;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * StringWriter
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class StringWriter implements Result {

    private Object value;
    private Map<String, Object> params = new HashMap<>();

    public StringWriter(Object value) {
        this.value = value;
    }

    @Override
    public StringWriter param(String key, Object object) {
        params.put(key, object);
        return this;
    }

    @Override
    public StringWriter params(Map<String, Object> objects) {
        objects.putAll(objects);
        return this;
    }

    @Override
    public void render(String contentPath, OutputStream out) {
        PrintWriter writer = new PrintWriter(out);
        writer.print(String.valueOf(value));
        writer.close();
    }
}
