package org.amhzing.clusterview.backend.infra.jpa.mapping;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {

    @Version
    protected int version;
}
