package org.amhzing.clusterview.backend.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class SimpleResponse {

    private String id;
    private String response;

    private SimpleResponse(final String id, final String response) {
        this.id = notBlank(id);
        this.response = notBlank(response);
    }

    public static SimpleResponse create(final String response) {
        return new SimpleResponse("", response);
    }

    public static SimpleResponse create(final String id, final String response) {
        return new SimpleResponse(id, response);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
        this.response = response;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleResponse)) return false;
        final SimpleResponse that = (SimpleResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, response);
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "id='" + id + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
