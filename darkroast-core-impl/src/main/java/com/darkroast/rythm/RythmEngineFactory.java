package com.darkroast.rythm;

import com.darkroast.config.ApplicationConfig;
import com.darkroast.servlet.annotations.Initialized;
import com.darkroast.servlet.events.ServletContextEvent;
import org.rythmengine.RythmEngine;
import org.rythmengine.template.ITemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * RythmEngineFactory
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
@ApplicationScoped
public class RythmEngineFactory {

    private static final Logger LOG = Logger.getLogger(RythmEngineFactory.class.getName());

    private static RythmEngine engine;

    @Inject ApplicationConfig applicationConfig;
    @Inject @Any Instance<ITemplate> tags;

    protected void initializeRythmEngine(@Observes @Initialized ServletContextEvent e) {
        String engineMode = applicationConfig.getString("darkroast.rythm.engine.mode");
        LOG.info("Initializing Rythm Template Engine in " + engineMode + " mode");

        String templateHome = applicationConfig.getString("darkroast.rythm.template.home");
        String contextRoot = e.getServletContext().getRealPath("/");
        Path path = Paths.get(contextRoot, templateHome);
        LOG.info("Template dir = " + path);

        Map<String, Object> conf = new HashMap<>();
        conf.put("rythm.engine.mode", engineMode);
        conf.put("rythm.home.template.dir", path.toFile());

        engine = new RythmEngine(conf);

        for (ITemplate tag : tags) {
            LOG.info("Registering template @" + tag.__getName());
            engine.registerTemplate(tag);
        }
    }

    public static RythmEngine getRythmEngine() {
        return engine;
    }
}
