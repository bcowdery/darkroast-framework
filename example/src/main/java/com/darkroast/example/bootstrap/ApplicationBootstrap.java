package com.darkroast.example.bootstrap;

import com.darkroast.servlet.annotations.Destroyed;
import com.darkroast.servlet.annotations.Initialized;
import com.darkroast.servlet.events.BootstrapEvent;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

/**
 * ApplicationBootstrap
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
@Startup
@ApplicationScoped
public class ApplicationBootstrap {

    private static final Logger LOG = Logger.getLogger(ApplicationBootstrap.class.getName());

    protected void applicationStartup(@Observes @Initialized BootstrapEvent e) {
        LOG.info("Example application is starting up!");
    }

    protected void applicationShutdown(@Observes @Destroyed BootstrapEvent event) {
        LOG.info("Example application is shutting down");
    }
}
