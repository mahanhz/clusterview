package org.amhzing.clusterview.app.web;

import org.springframework.http.MediaType;

import java.io.Serializable;

public final class CustomMediaType extends MediaType implements Serializable {

    public final static MediaType APPLICATION_JSON_V1;
    public final static MediaType APPLICATION_VND_ERROR_JSON;

    public final static String APPLICATION_JSON_V1_VALUE = "application/vnd.amhzing.clusterview.v1+json";
    public final static String APPLICATION_VND_ERROR_JSON_VALUE = "application/vnd.error+json";

    static {
        APPLICATION_JSON_V1 = valueOf(APPLICATION_JSON_V1_VALUE);
        APPLICATION_VND_ERROR_JSON = valueOf(APPLICATION_VND_ERROR_JSON_VALUE);
    }

    public CustomMediaType(final String type) {
        super(type);
    }
}
