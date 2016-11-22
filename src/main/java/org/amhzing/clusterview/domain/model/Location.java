package org.amhzing.clusterview.domain.model;

import java.util.Objects;

public final class Location {

    private double coordX;
    private double coordY;

    private Location(final double coordX, final double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static Location create(final double coordX, final double coordY) {
        return new Location(coordX, coordY);
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Location location = (Location) o;
        return Double.compare(location.coordX, coordX) == 0 &&
                Double.compare(location.coordY, coordY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY);
    }

    @Override
    public String toString() {
        return "Location{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}
