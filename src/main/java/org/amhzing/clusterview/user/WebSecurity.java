package org.amhzing.clusterview.user;

import org.amhzing.clusterview.domain.model.Country;
import org.springframework.security.core.Authentication;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class WebSecurity {

    public boolean checkCountry(final Authentication authentication, final String country) {
        notNull(authentication);
        notBlank(country);

        final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();

        final Country.Id countryId = Country.Id.create(country);

        return userDetails.getCountries().contains(countryId);
    }
}
