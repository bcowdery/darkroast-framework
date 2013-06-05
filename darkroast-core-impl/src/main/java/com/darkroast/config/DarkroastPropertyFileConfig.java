package com.darkroast.config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

/**
 * Load properties from the 'darkroast.properties' file.
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
public class DarkroastPropertyFileConfig implements PropertyFileConfig {

    @Override
    public String getPropertyFileName() {
        return "darkroast.properties";
    }
}
