package com.darkroast.dispatch;

/**
 * Route information about the current request.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class Route {

    private String controller = null;
    private String action = "index";

    public Route() {
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "controller: " + getController() + " action: " + getAction();
    }
}
