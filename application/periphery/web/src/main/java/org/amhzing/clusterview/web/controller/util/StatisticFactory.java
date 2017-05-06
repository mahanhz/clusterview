package org.amhzing.clusterview.web.controller.util;

import org.amhzing.clusterview.core.domain.Course;
import org.amhzing.clusterview.core.domain.statistic.*;
import org.amhzing.clusterview.web.api.compare.CourseComparator;
import org.amhzing.clusterview.web.api.statistic.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public final class StatisticFactory {

    private StatisticFactory() {
        // Prevent instantiation
    }


    public static ActivitiesDTO activitiesDto(final ActivityStatistic statistics) {
        return new ActivitiesDTO(activityQuantities(statistics), coreActivities(statistics));
    }

    public static List<CoreActivityDTO> coreActivities(final ActivityStatistic statistics) {
        final Set<CoreActivity> coreActivities = statistics.getCoreActivities();

        return coreActivities.stream()
                             .map(StatisticFactory::coreActivityDto)
                             .sorted(comparing(a -> a.name))
                             .collect(Collectors.toList());
    }

    public static CoursesDTO coursesDto(final CourseStatistic statistics) {
        final List<CourseDTO> courses = statistics.getCourseQuantity()
                                                  .entrySet()
                                                  .stream()
                                                  .map(StatisticFactory::course)
                                                  .sorted(new CourseComparator())
                                                  .collect(toList());

        return new CoursesDTO(courses);
    }

    public static HistoricalActivitiesDTO historicalActivitiesDto(final ActivityStatistic currentStats,
                                                                  final List<DatedActivityStatistic> datedStats) {
        return new HistoricalActivitiesDTO(activitiesDto(currentStats),
                                           sortedDatedActivities(datedStats));
    }

    private static List<DatedActivitiesDTO> sortedDatedActivities(final List<DatedActivityStatistic> datedStats) {
        final List<DatedActivitiesDTO> collectedHistoryStats = datedStats.stream()
                                                                         .map(StatisticFactory::datedActivitiesDTO)
                                                                         .sorted(comparing(historicalActivitiesDTO -> historicalActivitiesDTO.date))
                                                                         .collect(toList());

        return collectedHistoryStats;
    }

    private static DatedActivitiesDTO datedActivitiesDTO(final DatedActivityStatistic stat) {
        return new DatedActivitiesDTO(stat.getDate(), activitiesDto(stat.getActivityStatistic()));
    }

    private static CourseDTO course(final Map.Entry<Course, Quantity> entry) {
        return new CourseDTO(entry.getKey().getId().getId(),
                             entry.getKey().getName(),
                             (int) entry.getValue().getValue());
    }

    private static List<ActivityDTO> activityQuantities(final ActivityStatistic statistics) {
        return statistics.getActivityQuantity()
                  .entrySet()
                  .stream()
                  .map(entry -> new ActivityDTO(entry.getKey().getName(), (int) entry.getValue().getValue()))
                  .collect(toList());
    }

    private static CoreActivityDTO coreActivityDto(final CoreActivity coreActivity) {
        return new CoreActivityDTO(coreActivity.getId().getId(),
                                   coreActivity.getName(),
                                   (int) coreActivity.getQuantity().getValue(),
                                   (int) coreActivity.getTotalParticipants().getValue(),
                                   (int) coreActivity.getCommunityOfInterest().getValue());
    }
}
