package org.amhzing.clusterview.domain.statistic;

import org.amhzing.clusterview.domain.Course;

import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.Validate.notNull;

public class CourseStatistic {

    private final Map<Course, Quantity> courseQuantity;

    private CourseStatistic(final Map<Course, Quantity> courseQuantity) {
        this.courseQuantity = notNull(courseQuantity);
    }

    public static CourseStatistic create(final Map<Course, Quantity> courseQuantity) {
        return new CourseStatistic(courseQuantity);
    }

    public Map<Course, Quantity> getCourseQuantity() {
        return courseQuantity;
    }

    public static CourseStatistic empty() {
        return CourseStatistic.create(emptyMap());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseStatistic)) return false;
        final CourseStatistic that = (CourseStatistic) o;
        return Objects.equals(courseQuantity, that.courseQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseQuantity);
    }

    @Override
    public String toString() {
        return "CourseStatistic{" +
                "courseQuantity=" + courseQuantity +
                '}';
    }
}
