package com.darkroast.model;

import com.darkroast.util.bean.ImmutableDelegatingBean;
import org.apache.deltaspike.core.api.literal.AnyLiteral;
import org.apache.deltaspike.core.api.literal.DefaultLiteral;
import org.apache.deltaspike.core.api.literal.ModelLiteral;
import org.apache.deltaspike.core.util.bean.BeanBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * An extension that delegates creation of {@link Model} beans to the  {@link ModelBeanProducer}
 * by dynamically registering new {@link ImmutableDelegatingBean} producer beans for annotated injection
 * points.
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public class ModelBeanExtension implements Extension {

    private static final Logger LOG = Logger.getLogger(ModelBeanExtension.class.getName());

    private Bean<Object> producer;
    private Set<Class<?>> targetTypes = new HashSet<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setProducer(Bean producer) {
        this.producer = producer;
    }

    void processViewModelProducer(@Observes ProcessProducerMethod<Object, ModelBeanProducer> event) {
        if (event.getAnnotatedProducerMethod().getBaseType().equals(Object.class)
                && event.getAnnotatedProducerMethod().isAnnotationPresent(Model.class)) {
            setProducer(event.getBean());
        }
    }

    void processViewModelProducerInverted(@Observes ProcessProducerMethod<ModelBeanProducer, Object> event) {
        AnnotatedMethod<?> method = event.getAnnotatedProducerMethod();
        if (event.getAnnotatedProducerMethod().getBaseType().equals(Object.class)
                && event.getAnnotatedProducerMethod().isAnnotationPresent(Model.class)) {
            setProducer(event.getBean());
        }
    }

    <X> void detectInjections(@Observes ProcessInjectionTarget<X> event) {
        for (InjectionPoint ip : event.getInjectionTarget().getInjectionPoints()) {
            Annotated annotated = ip.getAnnotated();

            if (annotated.isAnnotationPresent(Model.class)) {
                Collection<Annotation> allowed = Arrays.asList(
                        new DefaultLiteral(),
                        new AnyLiteral(),
                        annotated.getAnnotation(Model.class)
                );

                for (Annotation q : ip.getQualifiers()) {
                    if (!allowed.contains(q)) {
                        event.addDefinitionError(new IllegalArgumentException("No beans found for qualifiers"));
                        break;
                    }
                }

                Type targetType = getActualBeanType(ip.getType());
                if (!(targetType instanceof Class)) {
                    event.addDefinitionError(new IllegalArgumentException("Type cannot be a primitive."));
                    break;
                }

                Class<?> targetClass = (Class<?>) targetType;
                targetTypes.add(targetClass);

                LOG.info("Processed injection point for " + targetClass.getName());
            }
        }
    }

    private Type getActualBeanType(Type type) {
        if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Instance.class)) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return type;
    }

    void installBeans(@Observes AfterBeanDiscovery event, BeanManager beanManager) {
        if (producer != null) {
            for (Class<?> type : targetTypes) {
                LOG.info("Installing " + type.getSimpleName() + " @Producer " + producer.getBeanClass());
                event.addBean(createProducerBean(producer, type, new ModelLiteral(), beanManager));
            }
        }
    }

    private <T> Bean<T> createProducerBean(Bean<Object> delegate, Class<T> targetType, Annotation qualifier, BeanManager beanManager) {
        AnnotatedType<T> annotatedType = beanManager.createAnnotatedType(targetType);
        BeanBuilder<T> beanBuilder = new BeanBuilder<T>(beanManager).readFromType(annotatedType).qualifiers(qualifier);
        return new ImmutableDelegatingBean<>(delegate, beanBuilder);
    }
}
