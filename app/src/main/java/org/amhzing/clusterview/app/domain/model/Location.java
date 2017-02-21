package org.amhzing.clusterview.app.domain.model;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable
public interface Location {
    int coordX();
    int coordY();
}
