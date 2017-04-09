package org.amhzing.clusterview.app.domain.model.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;

@Value.Immutable
public interface Email {
    int MAX_LENGTH = 65;

    @Value.Parameter
    String value();

    @Value.Check
    default Email check() {
        final String trimmed = trim(value());
        isTrue(trimmed.length() <= MAX_LENGTH && EmailValidator.getInstance().isValid(trimmed));

        if (!StringUtils.equals(value(), trimmed)) {
            return ImmutableEmail.of(trimmed);
        }

        return this;
    }
}
