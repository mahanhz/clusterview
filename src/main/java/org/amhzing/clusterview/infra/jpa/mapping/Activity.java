package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activity {

    @Id
    private String id;

    private String name;

    public Activity() {
    }

    private Activity(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static Activity create(final String id, final String name) {
        return new Activity(id, name);
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
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
