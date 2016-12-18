package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.event.AuthenticationFailureEventListener;
import org.amhzing.clusterview.event.AuthenticationSuccessEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import javax.servlet.http.HttpSession;

@Configuration
public class EventConfig {

    @Autowired
    private HttpSession httpSession;

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessEvent() {
        return new AuthenticationSuccessEventListener(httpSession);
    }

    @Bean
    public ApplicationListener<AuthenticationFailureBadCredentialsEvent> authenticationFailureEvent() {
        return new AuthenticationFailureEventListener();
    }
}
