package org.amhzing.clusterview.appui.web.model;

import javax.validation.constraints.Min;
import java.util.Objects;

public final class LocationModel {

    @Min(value = 1, message = "Location x is required")
    private int coordX;
    @Min(value = 1, message = "Location y is required")
    private int coordY;

    public LocationModel() {
    }

    private LocationModel(final int coordX, final int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static LocationModel create(final int coordX, final int coordY) {
        return new LocationModel(coordX, coordY);
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(final int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(final int coordY) {
        this.coordY = coordY;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LocationModel that = (LocationModel) o;
        return coordX == that.coordX &&
                coordY == that.coordY;
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
