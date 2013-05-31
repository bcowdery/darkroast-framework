package com.darkroast.config;

import com.darkroast.servlet.annotations.Destroyed;
import com.darkroast.servlet.annotations.Initialized;
import com.darkroast.servlet.events.ServerContextEvent;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Allows the web-application to provide bootstrapping hooks for startup and
 * shutdown events.
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
@ApplicationScoped
public class ApplicationBootstrapper {

    @Inject Instance<Bootstrap> bootstrappers;

    protected void applicationStartup(@Observes @Initialized ServerContextEvent e) {
        for (Bootstrap bootstrap : bootstrappers) {
            bootstrap.startup();
        }
    }

    protected void applicationShutdown(@Observes @Destroyed ServletContextEvent event) {
        for (Bootstrap bootstrap : bootstrappers) {
            bootstrap.shutdown();
        }
    }


    /**
     * A default bootstrap implementation that prints out the framework version and
     * some information about the web application deployment.
     */
    @Named
    static class DarkroastInfo implements Bootstrap {

        @Inject ServletContext servletContext;

        @Override
        public void startup() {
            System.out.println("  ____             _    ____                 _   ");
            System.out.println(" |  _ \\  __ _ _ __| | _|  _ \\ ___   __ _ ___| |_");
            System.out.println(" | | | |/ _` | '__| |/ / |_) / _ \\ / _` / __| __|");
            System.out.println(" | |_| | (_| | |  |   <|  _ < (_) | (_| \\__ \\ |_");
            System.out.println(" |____/ \\__,_|_|  |_|\\_\\_| \\_\\___/ \\__,_|___/\\__| \n");
            System.out.println(servletContext.getServletContextName());
        }

        @Override
        public void shutdown() {
        }
    }
}
