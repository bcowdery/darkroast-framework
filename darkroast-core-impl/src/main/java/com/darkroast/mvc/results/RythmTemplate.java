package com.darkroast.mvc.results;

import org.rythmengine.RythmEngine;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Result that renders content using the <a href="http://www.rythmengine.com/">Rythm template engine</a>.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class RythmTemplate extends AbstractContentResult {

    private static RythmEngine engine = null;

    private String view;
    private Map<String, Object> params = new HashMap<>();


    public RythmTemplate(String view) {
        this.view = view;
    }

    public RythmTemplate(String view, Object model) {
        this.view = view;
        this.params.put("model", model);
    }


    @Override
    public RythmTemplate add(String key, Object object) {
        params.put(key, object);
        return this;
    }

    @Override
    public RythmTemplate add(Map<String, Object> objects) {
        objects.putAll(objects);
        return this;
    }

    @Override
    public void render(HttpServletResponse response, String contentPath) throws IOException {
        response.setContentType(contentType);
        getRythmEngine(contentPath).render(response.getOutputStream(), view, params);
    }

    private RythmEngine getRythmEngine(String contentPath) {
        if (null == engine) {
            Map<String, Object> conf = new HashMap<>();
            conf.put("home.template", contentPath);

            engine = new RythmEngine(conf);
        }

        return engine;
    }
}
