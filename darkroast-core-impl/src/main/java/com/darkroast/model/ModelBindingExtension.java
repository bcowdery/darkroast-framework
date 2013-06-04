package com.darkroast.model;

import com.darkroast.annotations.ViewModel;
import com.darkroast.util.bean.ImmutableDelegatingBean;
import org.apache.deltaspike.core.util.bean.*;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.*;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An extension that delegates creation of {@link ViewModel} beans to the  {@link ViewModelProducer}
 * by dynamically registering new {@link ImmutableDelegatingBean} producers for annotated injection points.
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
public class ModelBindingExtension implements Extension {

    // todo: Ugly ugly very ugly... But it works. Clean this up in any way possible.

    static class InternalDefaultLiteral extends AnnotationLiteral<Default> implements Default {
        public static final Default INSTANCE = new InternalDefaultLiteral();
    }

    static class InternalAnyLiteral extends AnnotationLiteral<Any> implements Any {
        public static final Any INSTANCE = new InternalAnyLiteral();
    }

    static class ViewModelLiteral extends AnnotationLiteral<ViewModel> implements ViewModel {
        public static final ViewModel INSTANCE = new ViewModelLiteral();
    }



    private Bean<Object> producer;
    private Set<Class<?>> targetTypes = new HashSet<Class<?>>();

    void processViewModelProducer(@Observes ProcessProducerMethod<Object, ViewModelProducer> event) {
        if (event.getAnnotatedProducerMethod().getBaseType().equals(Object.class)
                && event.getAnnotatedProducerMethod().isAnnotationPresent(ViewModel.class)) {
            setProducer(event.getBean());
        }
    }

    void processViewModelProducerInverted(@Observes ProcessProducerMethod<ViewModelProducer, Object> event) {
        AnnotatedMethod<?> method = event.getAnnotatedProducerMethod();
        if (event.getAnnotatedProducerMethod().getBaseType().equals(Object.class)
                && event.getAnnotatedProducerMethod().isAnnotationPresent(ViewModel.class)) {
            setProducer(event.getBean());
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setProducer(Bean producer) {
        this.producer = producer;
    }


    <X> void detectInjections(@Observes ProcessInjectionTarget<X> event) {
        for (InjectionPoint ip : event.getInjectionTarget().getInjectionPoints()) {
            Annotated annotated = ip.getAnnotated();

            if (annotated.isAnnotationPresent(ViewModel.class)) {
                Collection<Annotation> allowed = Arrays.asList(
                        InternalDefaultLiteral.INSTANCE,
                        InternalAnyLiteral.INSTANCE,
                        annotated.getAnnotation(ViewModel.class)
                );

                boolean error = false;
                for (Annotation q : ip.getQualifiers()) {
                    if (!allowed.contains(q)) {
                        event.addDefinitionError(new IllegalArgumentException("No beans found for qualifiers"));
                        error = true;
                        break;
                    }
                }

                if (error) {
                    break;
                }

                Type targetType = getActualBeanType(ip.getType());
                if (!(targetType instanceof Class)) {
                    event.addDefinitionError(new IllegalArgumentException("Type cannot be a primitive."));
                    break;
                }

                Class<?> targetClass = (Class<?>) targetType;
                targetTypes.add(targetClass);
            }
        }
    }

    private Type getActualBeanType(Type t) {
        if (t instanceof ParameterizedType && ((ParameterizedType) t).getRawType().equals(Instance.class)) {
            return ((ParameterizedType) t).getActualTypeArguments()[0];
        }
        return t;
    }

    void installBeans(@Observes AfterBeanDiscovery event, BeanManager beanManager) {
        if (producer != null) {
            for (Class<?> type : targetTypes) {
                event.addBean(createProducerBean(producer, type, ViewModelLiteral.INSTANCE, beanManager));
            }
        }
    }

    private <T> Bean<T> createProducerBean(Bean<Object> delegate, Class<T> targetType, Annotation qualifier, BeanManager beanManager) {
        AnnotatedType<T> annotatedType = beanManager.createAnnotatedType(targetType);
        BeanBuilder<T> beanBuilder = new BeanBuilder<T>(beanManager).readFromType(annotatedType).qualifiers(qualifier);
        return new ImmutableDelegatingBean<T>(delegate, beanBuilder);
    }

}
