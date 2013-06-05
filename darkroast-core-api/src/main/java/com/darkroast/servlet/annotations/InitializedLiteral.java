package com.darkroast.servlet.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal for {@link Initialized}
 *
 * @author Brian Cowdery
 * @since 30-05-2013
 */
public class InitializedLiteral extends AnnotationLiteral<Initialized> implements Initialized {
    public static InitializedLiteral INSTANCE = new InitializedLiteral();
}
