package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Course;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.infra.jpa.mapping.CourseEntity;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

public final class ClusterEntityFactory {

    private ClusterEntityFactory() {
        // prevent instantiation
    }

    public static Map<CourseEntity, Integer> courses(final CourseStatistic courseStatistic) {
        return courseStatistic.getCourseQuantity()
                              .entrySet()
                              .stream()
                              .collect(toMap(ClusterEntityFactory::courseEntity, ClusterEntityFactory::quantity));
    }

    private static CourseEntity courseEntity(final Map.Entry<Course, Quantity> entry) {
        final Course course = entry.getKey();

        final CourseEntity entity = new CourseEntity();
        entity.setId(course.getId().getId());
        entity.setName(course.getName());

        return entity;
    }

    private static Integer quantity(final Map.Entry<Course, Quantity> entry) {
        final Quantity quantity = entry.getValue();

        return (int) quantity.getValue();
    }
}
