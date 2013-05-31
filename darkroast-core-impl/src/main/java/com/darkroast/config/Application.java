package com.darkroast.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Holds information about the Application.
 *
 * @author Brian Cowdery
 * @since 06-03-2013
 */
@Named
@ApplicationScoped
public class Application {

    private static final String ENVIRONMENT = "darkroast.environment";
    private static final String ENVIRONMENT_DEFAULT = "DEVELOPMENT";

    private static final String VERSION = "darkroast.application.version";
    private static final String VERSION_DEFAULT = "1.0-SNAPSHOT";

    private Configuration config;
    private Environment environment;
    private String version;


    public Application() {
        try {
            config = new PropertiesConfiguration("darkroast.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("Could not load configuration 'darkroast.properties' file!", e);
        }

        environment = Environment.valueOf(config.getString(ENVIRONMENT, ENVIRONMENT_DEFAULT).toUpperCase());
        version = config.getString(VERSION, VERSION_DEFAULT);
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
