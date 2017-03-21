package org.amhzing.clusterview.app.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CoursesDTO extends ResourceSupport {

    @JsonProperty("courses")
    public final List<CourseDTO> courses;

    @JsonCreator
    public CoursesDTO(final List<CourseDTO> courses) {
        this.courses = noNullElements(courses);
    }
}
