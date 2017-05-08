package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.CoursesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.HistoricalActivitiesDTO;
import org.amhzing.clusterview.core.boundary.enter.StatisticHistoryService;
import org.amhzing.clusterview.core.boundary.enter.StatisticService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;

import java.util.List;

import static org.amhzing.clusterview.adapter.web.util.StatisticFactory.activitiesDto;
import static org.amhzing.clusterview.adapter.web.util.StatisticFactory.coursesDto;
import static org.amhzing.clusterview.adapter.web.util.StatisticFactory.historicalActivitiesDto;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class StatisticAdapter {

    private StatisticHistoryService statisticHistoryService;
    private StatisticService<ActivityStatistic> statisticService;
    private StatisticService<CourseStatistic> courseStatisticService;

    public StatisticAdapter(final StatisticHistoryService statisticHistoryService,
                            final StatisticService<ActivityStatistic> statisticService,
                            StatisticService<CourseStatistic> courseStatisticService) {
        this.statisticHistoryService = notNull(statisticHistoryService);
        this.statisticService = notNull(statisticService);
        this.courseStatisticService = notNull(courseStatisticService);
    }

    public ActivitiesDTO countryActivityStatistics(final String countryId) {
        notBlank(countryId);

        final ActivityStatistic statistics = statisticService.statistics(Country.Id.create(countryId));

        return activitiesDto(statistics);
    }

    public CoursesDTO countryCourseStatistics(final String countryId) {
        notBlank(countryId);

        final CourseStatistic statistics = courseStatisticService.statistics(Country.Id.create(countryId));

        return coursesDto(statistics);
    }

    public ActivitiesDTO regionActivityStatistics(final String regionId) {
        notBlank(regionId);

        final ActivityStatistic statistics = statisticService.statistics(Region.Id.create(regionId));

        return activitiesDto(statistics);
    }

    public CoursesDTO regionCourseStatistics(final String regionId) {
        notBlank(regionId);

        final CourseStatistic statistics = courseStatisticService.statistics(Region.Id.create(regionId));

        return coursesDto(statistics);
    }

    public ActivitiesDTO clusterActivityStatistics(final String clusterId) {
        notBlank(clusterId);

        final ActivityStatistic statistics = statisticService.statistics(Cluster.Id.create(clusterId));

        return activitiesDto(statistics);
    }

    public CoursesDTO clusterCourseStatistics(final String clusterId) {
        notBlank(clusterId);

        final CourseStatistic statistics = courseStatisticService.statistics(Cluster.Id.create(clusterId));

        return coursesDto(statistics);
    }

    public HistoricalActivitiesDTO clusterHistory(final String clusterId) {
        notBlank(clusterId);

        final Cluster.Id cluster = Cluster.Id.create(clusterId);

        final ActivityStatistic currentStats = statisticService.statistics(cluster);
        final List<DatedActivityStatistic> datedStats = statisticHistoryService.history(cluster);

        return historicalActivitiesDto(currentStats, datedStats);
    }

    public void saveHistory(final String clusterId) {
        notBlank(clusterId);

        final Cluster.Id cluster = Cluster.Id.create(clusterId);

        final ActivityStatistic clusterStats = statisticService.statistics(cluster);

        statisticHistoryService.saveHistory(cluster, clusterStats);
    }
}
