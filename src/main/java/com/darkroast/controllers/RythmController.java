package com.darkroast.controllers;

import com.darkroast.mvc.Controller;
import com.darkroast.mvc.annotations.Path;
import com.darkroast.results.Result;

import static com.darkroast.results.Results.*;

/**
 * RythmController
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@Path("rythm")
public class RythmController implements Controller {

    @Path("index")
    public Result index() {
        return view("index.html").param("what", "Rythm");
    }
}
