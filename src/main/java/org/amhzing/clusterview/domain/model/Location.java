package org.amhzing.clusterview.domain.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Location {
    int coordX();
    int coordY();
}
