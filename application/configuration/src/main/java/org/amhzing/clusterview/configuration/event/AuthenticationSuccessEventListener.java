package org.amhzing.clusterview.configuration.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        final DefaultUserDetails userDetails = (DefaultUserDetails) event.getAuthentication().getPrincipal();
        LOGGER.info("Successful login for: {}, {} {}", userDetails.getUsername(), userDetails.getFirstName(), userDetails.getLastName());
    }
}
