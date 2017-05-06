package org.amhzing.clusterview.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Inherited
@ConditionalOnProperty(prefix = "clusterview.rest", name = "enabled")
public @interface ConditionalOnRestEnabled {

}
