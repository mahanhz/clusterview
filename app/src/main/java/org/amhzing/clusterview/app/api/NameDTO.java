package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class NameDTO {

    @JsonProperty("firstName")
    public final String firstName;
    @JsonProperty("middleName")
    public final String middleName;
    @JsonProperty("lastName")
    public final String lastName;
    @JsonProperty("suffix")
    public final String suffix;

    @JsonCreator
    public NameDTO(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = notBlank(firstName);
        this.middleName = middleName;
        this.lastName = notBlank(lastName);
        this.suffix = suffix;
    }
}
