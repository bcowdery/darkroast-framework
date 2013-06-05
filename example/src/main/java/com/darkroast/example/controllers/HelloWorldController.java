package com.darkroast.example.controllers;

import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.annotations.ViewModel;
import com.darkroast.example.model.Person;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.results.Result;

import javax.inject.Inject;

import static com.darkroast.mvc.results.Results.view;

/**
 * RythmController
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@Path("rythm")
public class HelloWorldController implements Controller {

    @Inject @ViewModel Person person;

    @Path("index")
    public Result index() {
        System.out.println("Person name: " + person.getName());
        return view("index.html", "Rythm");
    }
}
