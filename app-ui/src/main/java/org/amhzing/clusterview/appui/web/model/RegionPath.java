package org.amhzing.clusterview.appui.web.model;

import org.hibernate.validator.constraints.NotBlank;

public class RegionPath {

    @NotBlank
    private String country;

    @NotBlank
    private String region;

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "RegionPath{" +
                "country='" + country + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
