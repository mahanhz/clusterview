package org.amhzing.clusterview.controller.util;

import org.amhzing.clusterview.api.NameDTO;
import org.amhzing.clusterview.domain.*;

public final class NameDtoFactory {

    private NameDtoFactory() {
    }

    public static NameDTO convertName(final Name name) {

        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return new NameDTO(firstName == null ? "" : firstName.value(),
                           middleName == null ? "" : middleName.value(),
                           lastName == null ? "" : lastName.value(),
                           suffix == null ? "" : suffix.value());
    }
}
