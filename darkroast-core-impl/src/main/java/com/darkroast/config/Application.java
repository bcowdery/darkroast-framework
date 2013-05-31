package com.darkroast.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * Holds information about the Application.
 *
 * @author Brian Cowdery
 * @since 06-03-2013
 */
@Named
@ApplicationScoped
public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private CompositeConfiguration config;
    private Environment environment;
    private String version;

    public Application() {
        config = new CompositeConfiguration();
        _addConfiguration("darkroast.properties");
        _addConfiguration("darkroast-default.properties");

        environment = Environment.valueOf(config.getString("darkroast.environment").toUpperCase());
        version = config.getString("darkroast.version");
    }

    private void _addConfiguration(String propertiesFile) {
        try {
            config.addConfiguration(new PropertiesConfiguration(propertiesFile));
        } catch (ConfigurationException e) {
            LOG.warning("Properties file '" + propertiesFile + "' not found.");
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getVersion() {
        return version;
    }
}
