package com.darkroast.example.bootstrap;

import com.darkroast.config.Bootstrap;

import javax.inject.Named;

/**
 * ApplicationBootstrap
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
@Named
public class ApplicationBootstrap implements Bootstrap {

    @Override
    public void startup() {
        System.out.println("Application starting up!");
    }

    @Override
    public void shutdown() {
        System.out.println("Application shutting down.");
    }
}
