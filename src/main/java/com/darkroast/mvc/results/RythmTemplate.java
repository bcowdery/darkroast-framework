package com.darkroast.mvc.results;

import org.rythmengine.RythmEngine;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Object> objects = new ArrayList<>();

    public RythmTemplate(String view) {
        this.view = view;
    }

    @Override
    public RythmTemplate add(Object o) {
        objects.add(o);
        return this;
    }

    @Override
    public RythmTemplate add(List<Object> objects) {
        objects.addAll(objects);
        return this;
    }

    @Override
    public void render(String contentPath, OutputStream out) {
        Path template = Paths.get(contentPath).resolve(view);
        getRythmEngine().render(out, template.toFile(), objects.toArray());
    }

    private RythmEngine getRythmEngine() {
        if (null == engine) {
            Map<String, Object> conf = new HashMap<>();
            engine = new RythmEngine(conf);
        }

        return engine;
    }
}
