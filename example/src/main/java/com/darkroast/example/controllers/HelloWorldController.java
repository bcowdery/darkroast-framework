package com.darkroast.example.controllers;

import com.darkroast.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.results.Result;

import static com.darkroast.mvc.results.Results.view;

/**
 * RythmController
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@Path("rythm")
public class HelloWorldController implements Controller {

    @Path("index")
    public Result index() {
        return view("index.html", "Rythm");
    }
}
