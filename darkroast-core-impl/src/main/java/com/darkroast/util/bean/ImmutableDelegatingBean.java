package com.darkroast.util.bean;

import org.apache.deltaspike.core.util.bean.BeanBuilder;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An immutable implementation of {@link Bean} that allows you to build general purpose
 * beans by delegating function to an existing managed bean (likely a producer method). This
 * is useful for wiring non-specific producer methods to produce instances of an object that
 * matches the discovered criteria of the injected field. For example, a producer that creates
 * a new instance of an object matching the type of the field.
 *
 * <pre>
 *     @Produces
 *     @SomeQualifier
 *     public Object produce(InjectionPoint ip) {
 *         Class<?> type = (Class<?>) ip.getType()
 *         return type.newInstance();
 *     }
 * </pre>
 *
 * @see com.darkroast.model.ModelBeanExtension
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
public class ImmutableDelegatingBean<T> implements Bean<T> {

    private final Bean<Object> delegate;
    private final Class<?> beanClass;
    private final String name;
    private final Set<Type> types;
    private final Set<Annotation> qualifiers;
    private final Class<? extends Annotation> scope;
    private final Set<Class<? extends Annotation>> stereotypes;
    private final boolean alternative;
    private final boolean nullable;
    private final Set<InjectionPoint> injectionPoints;

    public ImmutableDelegatingBean(Bean<Object> delegate, BeanBuilder<T> beanBuilder) {
        this.delegate = delegate;
        this.beanClass = delegate.getBeanClass();
        this.name = beanBuilder.getName();
        this.types = new HashSet<>(beanBuilder.getTypes());
        this.qualifiers = new HashSet<>(beanBuilder.getQualifiers());
        this.scope = beanBuilder.getScope();
        this.stereotypes = new HashSet<>(beanBuilder.getStereotypes());
        this.alternative = beanBuilder.isAlternative();
        this.nullable = beanBuilder.isNullable();
        this.injectionPoints = delegate.getInjectionPoints();
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Type> getTypes() {
        return Collections.unmodifiableSet(types);
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return Collections.unmodifiableSet(qualifiers);
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return scope;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.unmodifiableSet(stereotypes);
    }

    @Override
    public boolean isAlternative() {
        return alternative;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return injectionPoints;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create(CreationalContext<T> creationalContext) {
        return (T) delegate.create((CreationalContext<Object>) creationalContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void destroy(T instance, CreationalContext<T> creationalContext) {
        delegate.destroy(instance, (CreationalContext<Object>) creationalContext);
    }
}
