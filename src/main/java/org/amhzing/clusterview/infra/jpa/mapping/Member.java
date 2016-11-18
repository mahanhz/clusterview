package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Name name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Capability> capability;

    public Member() {
    }

    private Member(final long id, final Name name, final Set<Capability> capability) {
        this.id = id;
        this.name = name;
        this.capability = capability;
    }

    public static Member create(final long id, final Name name, final Set<Capability> capability) {
        return new Member(id, name, capability);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    public Set<Capability> getCapability() {
        return capability;
    }

    public void setCapability(final Set<Capability> capability) {
        this.capability = capability;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name=" + name +
                ", capability=" + capability +
                '}';
    }
}
