package org.amhzing.clusterview.domain.model;

import java.util.Objects;

public class Location {

    private int coordX;
    private int coordY;

    private Location(final int coordX, final int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static Location create(final int coordX, final int coordY) {
        return new Location(coordX, coordY);
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Location location = (Location) o;
        return coordX == location.coordX &&
                coordY == location.coordY;
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
