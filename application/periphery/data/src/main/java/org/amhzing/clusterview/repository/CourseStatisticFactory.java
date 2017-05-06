package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.domain.Course;
import org.amhzing.clusterview.domain.statistic.Quantity;
import org.amhzing.clusterview.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.jpa.entity.CourseEntity;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.Validate.notNull;

public final class CourseStatisticFactory {

    private CourseStatisticFactory() {
        // To prevent instantiation
    }

    public static Map<Course, Quantity> courseStats(final Stream<ClusterEntity> clusterEntityStream) {
        notNull(clusterEntityStream);

        return combinedCourseStats(clusterEntityStream).entrySet()
                                                       .stream()
                                                       .collect(toMap(CourseStatisticFactory::course,
                                                                       CourseStatisticFactory::quantity));
    }

    public static Map<Course, Quantity> courseStats(final ClusterEntity cluster) {
        notNull(cluster);

        return cluster.getCourses()
                      .entrySet()
                      .stream()
                      .collect(toMap(CourseStatisticFactory::course,
                                     CourseStatisticFactory::quantity));
    }

    private static Map<CourseEntity, Integer> combinedCourseStats(final Stream<ClusterEntity> clusterEntityStream) {
        return clusterEntityStream.flatMap(cluster -> cluster.getCourses().entrySet().stream())
                                  .collect(groupingBy(Map.Entry::getKey,
                                                      summingInt(Map.Entry::getValue)));
    }

    private static Quantity quantity(final Map.Entry<CourseEntity, Integer> entry) {
        return Quantity.create(entry.getValue());
    }

    private static Course course(final Map.Entry<CourseEntity, Integer> entry) {
        final CourseEntity key = entry.getKey();

        return Course.create(Course.Id.create(key.getId()), key.getName());
    }
}
