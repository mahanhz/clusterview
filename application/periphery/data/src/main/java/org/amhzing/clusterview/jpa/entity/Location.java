package org.amhzing.clusterview.jpa.entity;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class Location {

    private int x;
    private int y;

    public Location() {
    }

    private Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Location create(final int x, final int y) {
        return new Location(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
