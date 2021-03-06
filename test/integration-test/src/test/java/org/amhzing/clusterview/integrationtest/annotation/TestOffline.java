package org.amhzing.clusterview.integrationtest.annotation;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ActiveProfiles("offline")
public @interface TestOffline {
}
