package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "activity")
public class ActivityEntity {

    @Id
    private String id;

    private String name;

    public ActivityEntity() {
    }

    private ActivityEntity(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static ActivityEntity create(final String id, final String name) {
        return new ActivityEntity(id, name);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ActivityEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
