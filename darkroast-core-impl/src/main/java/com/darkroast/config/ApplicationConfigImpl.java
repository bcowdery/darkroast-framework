package com.darkroast.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * Holds information about the ApplicationConfig.
 *
 * @author Brian Cowdery
 * @since 06-03-2013
 */
@Named
@ApplicationScoped
public class ApplicationConfigImpl implements ApplicationConfig {

    private static final Logger LOG = Logger.getLogger(ApplicationConfigImpl.class.getName());

    private CompositeConfiguration config;
    private Environment environment;

    public ApplicationConfigImpl() {
        config = new CompositeConfiguration();
        _addConfiguration("darkroast.properties");
        _addConfiguration("darkroast-default.properties");

        environment = Environment.valueOf(config.getString("darkroast.environment").toUpperCase());

    }

    private void _addConfiguration(String propertiesFile) {
        try {
            config.addConfiguration(new PropertiesConfiguration(propertiesFile));
        } catch (ConfigurationException e) {
            LOG.warning("Properties file '" + propertiesFile + "' not found.");
        }
    }

    public Environment getEnvironment() {
        return environment;
    }


    /*
        Simple property accessors
     */

    public String getString(String key) {
        return config.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return config.getInt(key, defaultValue);
    }

    public double getDouble(String key) {
        return config.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return config.getDouble(key, defaultValue);
    }

    public BigDecimal getBigDecimal(String key) {
        return config.getBigDecimal(key);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return config.getBigDecimal(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }
}
