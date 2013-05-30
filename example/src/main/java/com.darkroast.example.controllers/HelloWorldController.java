package com.darkroast.example.controllers;

import com.darkroast.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.Result;

import static com.darkroast.results.Results.*;

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
        return view("index.html").param("what", "Rythm");
    }
}
