package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.configuration.event.AuthenticationFailureEventListener;
import org.amhzing.clusterview.configuration.event.AuthenticationSuccessEventListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@Configuration
public class EventConfig {

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessEvent() {
        return new AuthenticationSuccessEventListener();
    }

    @Bean
    public ApplicationListener<AuthenticationFailureBadCredentialsEvent> authenticationFailureEvent() {
        return new AuthenticationFailureEventListener();
    }
}
