package org.amhzing.clusterview.appui.web.model.form;

import org.amhzing.clusterview.appui.web.model.CourseStatisticModel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;

public final class CourseStatisticsForm {

    @NotEmpty(message = "Course stastistic must not be empty")
    @Valid
    private List<CourseStatisticModel> courseStatistics;

    public CourseStatisticsForm() {
        courseStatistics = new ArrayList<>();
    }

    private CourseStatisticsForm(final List<CourseStatisticModel> courseStatistics) {
        this.courseStatistics = noNullElements(courseStatistics);
    }

    public static CourseStatisticsForm create(final List<CourseStatisticModel> courseStatistics) {
        return new CourseStatisticsForm(courseStatistics);
    }

    public boolean allZero() {
        return courseStatistics.stream()
                               .allMatch(CourseStatisticModel::isZero);
    }

    public List<CourseStatisticModel> getCourseStatistics() {
        return courseStatistics;
    }

    public void setCourseStatistics(final List<CourseStatisticModel> courseStatistics) {
        this.courseStatistics = courseStatistics;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseStatisticsForm)) return false;
        final CourseStatisticsForm that = (CourseStatisticsForm) o;
        return Objects.equals(courseStatistics, that.courseStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseStatistics);
    }

    @Override
    public String toString() {
        return "CourseStatisticsForm{" +
                "courseStatistics=" + courseStatistics +
                '}';
    }
}
