package com.darkroast.rythm;

import com.darkroast.config.ApplicationSettings;
import com.darkroast.rythm.annotations.RythmTag;
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
 * Initializes the <code>RythmEngine</code> and registers <code>ITemplate</code> tag
 * implementations annotated with {@link RythmTag}.
 *
 * darkroast.properties:
 * <pre>
 *  # rythm template home directory
 *  darkroast.rythm.template.home=/WEB-INF/content
 *
 *  # rythm engine mode
 *  darkroast.rythm.engine.mode=prod
 * </pre>
 *
 * @see com.darkroast.config.ApplicationSettingsBean
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
@ApplicationScoped
public class RythmEngineFactory {

    private static final Logger LOG = Logger.getLogger(RythmEngineFactory.class.getName());

    private static RythmEngine engine;

    @Inject ApplicationSettings applicationSettings;
    @Inject @RythmTag Instance<ITemplate> tags;

    protected void initializeRythmEngine(@Observes @Initialized ServletContextEvent e) {
        String engineMode = applicationSettings.getRythmEngineMode();
        LOG.info("Initializing Rythm Template Engine in " + engineMode + " mode");

        String templateHome = applicationSettings.getRythmTemplateHome();
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
