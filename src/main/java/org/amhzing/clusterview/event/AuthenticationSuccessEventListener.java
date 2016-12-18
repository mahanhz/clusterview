package org.amhzing.clusterview.event;

import org.amhzing.clusterview.user.DefaultUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.amhzing.clusterview.user.UserUtil.isSingleCountry;
import static org.apache.commons.lang3.Validate.notNull;

public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);

    private static final String USER_COUNTRY = "userCountry";

    private HttpSession session;

    public AuthenticationSuccessEventListener(final HttpSession session) {
        this.session = notNull(session);
    }

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        final DefaultUserDetails userDetails = (DefaultUserDetails) event.getAuthentication().getPrincipal();
        LOGGER.info("Successful login for: " + userDetails.getUsername() + ", " + userDetails.getFirstName() + " " + userDetails.getLastName());

        setUserCountryIntoSession(userDetails);
    }

    private void setUserCountryIntoSession(final DefaultUserDetails userDetails) {
        final List<String> countries = userDetails.getCountries();

        if (isSingleCountry(countries)) {
            session.setAttribute(USER_COUNTRY, countries.iterator().next());
        }
    }
}
