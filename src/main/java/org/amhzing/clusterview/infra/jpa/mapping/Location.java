package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Location {

    private double x;
    private double y;

    public Location() {
    }

    private Location(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public static Location create(final double x, final double y) {
        return new Location(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0;
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
