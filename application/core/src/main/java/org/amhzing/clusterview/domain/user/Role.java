package org.amhzing.clusterview.domain.user;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

@Value.Immutable
public interface Role {
    int MAX_LENGTH = 50;

    @Value.Parameter
    String value();

    @Value.Check
    default Role check() {
        notBlank(value());

        final String trimmed = trim(value());
        isTrue(trimmed.length() <= MAX_LENGTH);

        if (!StringUtils.equals(value(), trimmed)) {
            return ImmutableRole.of(trimmed);
        }

        return this;
    }
}
