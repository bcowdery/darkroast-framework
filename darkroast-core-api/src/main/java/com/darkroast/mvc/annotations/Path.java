package com.darkroast.mvc.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as an MVC controller that can be invoked at a URL. If used to
 * annotate a method on a controller, the method will be invoked as the action
 * for the path.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Path {

    String value() default "";

}
