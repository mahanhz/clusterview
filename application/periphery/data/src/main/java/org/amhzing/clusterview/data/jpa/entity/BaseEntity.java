package org.amhzing.clusterview.data.jpa.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {

    @Version
    protected int version;
}
