package com.darkroast.rythm.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rythm <code>ITemplate</code> tag implementations to be registered with the
 * <code>RythmEngine</code> at startup.
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface RythmTag {
}
