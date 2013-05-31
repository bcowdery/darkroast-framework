package com.darkroast.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal implementation of {@link Path} for finding qualified beans.
 *
 * @author Brian Cowdery
 * @since 29-05-2013
 */
public class PathLiteral extends AnnotationLiteral<Path> implements Path {

    private final String value;

    public PathLiteral(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
