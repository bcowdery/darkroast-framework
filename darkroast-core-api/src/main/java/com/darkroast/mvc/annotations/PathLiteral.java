package com.darkroast.mvc.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal for {@link Path}
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
