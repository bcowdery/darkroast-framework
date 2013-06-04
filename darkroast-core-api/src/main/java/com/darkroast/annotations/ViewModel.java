package com.darkroast.annotations;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a request scoped model for use in rendering Views. Fields annotated
 * with @ViewModel will be populated using submitted request parameters if the parameter
 * names resolve to a JavaBeans property name of the annotated field type.
 *
 * todo: can probably eliminate this annotaiton and use the standard @Model annotation
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface ViewModel {
}
