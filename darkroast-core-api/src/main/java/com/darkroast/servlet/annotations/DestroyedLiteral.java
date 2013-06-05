package com.darkroast.servlet.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal for {@link Destroyed}
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class DestroyedLiteral extends AnnotationLiteral<Destroyed> implements Destroyed {
    public static DestroyedLiteral INSTANCE = new DestroyedLiteral();
}
