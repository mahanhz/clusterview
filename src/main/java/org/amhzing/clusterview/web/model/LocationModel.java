package org.amhzing.clusterview.web.model;

import java.util.Objects;

public final class LocationModel {

    private double coordX;
    private double coordY;

    private LocationModel(final double coordX, final double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static LocationModel create(final double coordX, final double coordY) {
        return new LocationModel(coordX, coordY);
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(final double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(final double coordY) {
        this.coordY = coordY;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LocationModel that = (LocationModel) o;
        return Double.compare(that.coordX, coordX) == 0 &&
                Double.compare(that.coordY, coordY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY);
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}
