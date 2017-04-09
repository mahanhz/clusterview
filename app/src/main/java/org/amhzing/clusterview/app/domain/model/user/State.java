package org.amhzing.clusterview.app.domain.model.user;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable
public interface State {
    boolean active();
}
