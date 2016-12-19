package org.amhzing.clusterview.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureEventListener.class);

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent event) {
        final WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        final Object principal = event.getAuthentication().getPrincipal();

        LOGGER.info("Failed login for: {}, from: {}", principal, auth.getRemoteAddress());
    }
}
