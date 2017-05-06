package org.amhzing.clusterview.core.domain.user;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable
public interface State {
    boolean active();
}
