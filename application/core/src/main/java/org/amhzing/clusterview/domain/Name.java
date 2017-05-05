package org.amhzing.clusterview.domain;

import org.amhzing.clusterview.domain.FirstName;
import org.amhzing.clusterview.domain.LastName;
import org.amhzing.clusterview.domain.MiddleName;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public interface Name {
    FirstName firstName();
    @Nullable
    MiddleName middleName();
    LastName lastName();
    @Nullable
    Suffix suffix();
}
