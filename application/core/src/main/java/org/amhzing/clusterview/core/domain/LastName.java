package org.amhzing.clusterview.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

@Value.Immutable
public interface LastName {
    int MAX_LENGTH = 25;

    @Value.Parameter
    String value();

    @Value.Check
    default LastName check() {
        notBlank(value());

        final String trimmed = trim(value());
        isTrue(trimmed.length() <= MAX_LENGTH);

        if (!StringUtils.equals(value(), trimmed)) {
            return ImmutableLastName.of(trimmed);
        }

        return this;
    }
}
