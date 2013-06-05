package com.darkroast.config;

import java.math.BigDecimal;

/**
 * ApplicationSettings
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public interface ApplicationSettings {

    public Environment getEnvironment();
    public String getVersion();
    public String getDispatchUrlPattern();
    public String getDispatchRouteRegex();
    public String getRythmTemplateHome();
    public String getRythmEngineMode();
}