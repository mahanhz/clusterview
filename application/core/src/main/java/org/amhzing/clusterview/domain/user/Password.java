package org.amhzing.clusterview.domain.user;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.inclusiveBetween;

@Value.Immutable
public interface Password {
    int MIN_LENGTH = 8;
    int MAX_LENGTH = 15;

    @Value.Parameter
    String value();

    @Value.Check
    default Password check() {
        final String trimmed = trim(value());
        inclusiveBetween(MIN_LENGTH, MAX_LENGTH, trimmed.length());

        if (!StringUtils.equals(value(), trimmed)) {
            return ImmutablePassword.of(trimmed);
        }

        return this;
    }
}
