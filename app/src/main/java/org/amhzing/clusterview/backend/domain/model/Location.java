package org.amhzing.clusterview.backend.domain.model;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable
public interface Location {
    int coordX();
    int coordY();
}
