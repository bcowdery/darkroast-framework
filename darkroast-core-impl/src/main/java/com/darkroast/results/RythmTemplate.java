package com.darkroast.results;

import org.rythmengine.RythmEngine;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Result that renders content using the <a href="http://www.rythmengine.com/">Rythm template engine</a>.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class RythmTemplate implements Result {

    private static RythmEngine engine = null;

    private String view;
    private Map<String, Object> params = new HashMap<>();

    public RythmTemplate(String view) {
        this.view = view;
    }

    @Override
    public RythmTemplate param(String key, Object object) {
        params.put(key, object);
        return this;
    }

    @Override
    public RythmTemplate params(Map<String, Object> objects) {
        objects.putAll(objects);
        return this;
    }

    @Override
    public void render(String contentPath, OutputStream out) {
        getRythmEngine(contentPath).render(out, view, params);
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
