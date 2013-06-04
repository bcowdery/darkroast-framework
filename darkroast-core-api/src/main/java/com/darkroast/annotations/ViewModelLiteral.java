package com.darkroast.annotations;

import javax.enterprise.util.AnnotationLiteral;

/**
 * ViewModelLiteral
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
public class ViewModelLiteral extends AnnotationLiteral<ViewModel> implements ViewModel {
    public static final ViewModel INSTANCE = new ViewModelLiteral();
}
