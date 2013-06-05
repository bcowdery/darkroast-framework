package com.darkroast.config;

import org.apache.deltaspike.core.api.config.annotation.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Darkroast application settings.
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@ApplicationScoped
public class ApplicationSettingsBean implements ApplicationSettings {

    private Environment environment;

    @Inject
    @ConfigProperty(name = "darkroast.version", defaultValue = "1.0-SNAPSHOT")
    private String version;

    @Inject
    @ConfigProperty(name = "darkroast.dispatch.urlpattern", defaultValue = "/*")
    private String dispatchUrlPattern;

    @Inject
    @ConfigProperty(name = "darkroast.dispatch.routes", defaultValue = "^\\/(?<controller>\\w+)\\/?(?<action>\\w*)\\/?(?<id>\\w*)")
    private String dispatchRouteRegex;

    @Inject
    @ConfigProperty(name = "darkroast.rythm.template.home", defaultValue = "/WEB-INF/content")
    private String rythmTemplateHome;

    @Inject
    @ConfigProperty(name = "darkroast.rythm.engine.mode", defaultValue = "prod")
    private String rythmEngineMode;


    public ApplicationSettingsBean() {
    }


    @Inject
    protected void setEnvironment(@ConfigProperty(name = "darkroast.environment", defaultValue = "prod") String name) {
        this.environment = Environment.valueOf(name.toUpperCase());
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getVersion() {
        return version;
    }

    public String getDispatchUrlPattern() {
        return dispatchUrlPattern;
    }

    public String getDispatchRouteRegex() {
        return dispatchRouteRegex;
    }

    public String getRythmTemplateHome() {
        return rythmTemplateHome;
    }

    public String getRythmEngineMode() {
        return rythmEngineMode;
    }
}
