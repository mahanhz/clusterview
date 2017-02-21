package org.amhzing.clusterview.app.infra.jpa.mapping;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {

    @Version
    protected int version;
}
