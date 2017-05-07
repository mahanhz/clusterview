package org.amhzing.clusterview.configuration;

import static org.apache.commons.lang3.Validate.notBlank;

public enum StaticFiles {

    CSS("/css/", "/css/**"),
    JS("/js/", "/js/**"),
    IMAGES("/images/", "/images/**"),
    SE("/se/", "/se/**");

    private String resourceLocation;
    private String resourcePattern;

    StaticFiles(final String resourceLocation, final String resourcePattern) {
        this.resourceLocation = notBlank(resourceLocation);
        this.resourcePattern = notBlank(resourcePattern);
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }
}
