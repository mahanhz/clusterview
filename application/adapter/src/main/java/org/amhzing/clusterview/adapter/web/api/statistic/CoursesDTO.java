package org.amhzing.clusterview.adapter.web.api.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CoursesDTO {

    @JsonProperty("courses")
    public final List<CourseDTO> courses;

    public CoursesDTO(final List<CourseDTO> courses) {
        this.courses = noNullElements(courses);
    }
}
