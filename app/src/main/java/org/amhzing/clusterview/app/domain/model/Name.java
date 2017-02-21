package org.amhzing.clusterview.app.domain.model;

import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public interface Name {
    FirstName firstName();
    @Nullable MiddleName middleName();
    LastName lastName();
    @Nullable Suffix suffix();
}
