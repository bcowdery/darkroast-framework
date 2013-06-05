package com.darkroast.mvc.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal for {@link ViewModel}
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
public class ViewModelLiteral extends AnnotationLiteral<ViewModel> implements ViewModel {
    public static final ViewModel INSTANCE = new ViewModelLiteral();
}
