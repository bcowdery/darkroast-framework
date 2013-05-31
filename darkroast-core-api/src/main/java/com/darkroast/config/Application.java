package com.darkroast.config;

import org.apache.commons.configuration.Configuration;

/**
 * Application
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public interface Application {

    public String getVersion();
    public Environment getEnvironment();
    public Configuration getConfig(); // todo: refactor into "get" methods to remove dependency on commons-configuration

}
