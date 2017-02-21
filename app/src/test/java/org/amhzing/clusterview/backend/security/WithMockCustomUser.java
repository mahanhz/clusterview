package org.amhzing.clusterview.backend.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String value() default "user";

    String username() default "";

    String[] roles() default { "USER" };

    String[] authorities() default {};

    String password() default "password";

    String firstName() default "John";

    String lastName() default "Doe";
}
