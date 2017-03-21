package org.amhzing.clusterview.app.web.adapter;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.ClusterNameModel;
import org.amhzing.clusterview.app.web.model.CourseStatisticModel;
import org.amhzing.clusterview.app.web.model.DatedActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.form.CourseStatisticsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.app.web.adapter.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StatisticAdapter {

    private StatisticService<ActivityStatistic> statisticService;
    private StatisticService<CourseStatistic> courseStatisticService;
    private StatisticHistoryService statisticHistoryService;
    private ActivityService activityService;
    private ClusterService clusterService;

    @Autowired
    public StatisticAdapter(final StatisticService<ActivityStatistic> statisticService,
                            final StatisticService<CourseStatistic> courseStatisticService,
                            final StatisticHistoryService statisticHistoryService,
                            final ActivityService activityService,
                            final ClusterService clusterService) {
        this.statisticService = notNull(statisticService);
        this.courseStatisticService = notNull(courseStatisticService);
        this.statisticHistoryService = notNull(statisticHistoryService);
        this.activityService = notNull(activityService);
        this.clusterService = notNull(clusterService);
    }

    public ActivityStatisticModel countryStats(final String countryId) {
        notBlank(countryId);

        final ActivityStatistic statistics = statisticService.statistics(Country.Id.create(countryId));


        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public ActivityStatisticModel regionStats(final String regionId) {
        notBlank(regionId);

        final ActivityStatistic statistics = statisticService.statistics(Region.Id.create(regionId));

        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public ActivityStatisticModel clusterStats(final String clusterId) {
        notBlank(clusterId);

        final ActivityStatistic statistics = statisticService.statistics(Cluster.Id.create(clusterId));

        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public List<CourseStatisticModel> countryCourseStats(final String countryId) {
        notBlank(countryId);

        final CourseStatistic statistics = courseStatisticService.statistics(Country.Id.create(countryId));

        return courseStatisticsList(statistics);
    }

    public List<CourseStatisticModel> regionCourseStats(final String regionId) {
        notBlank(regionId);

        final CourseStatistic statistics = courseStatisticService.statistics(Region.Id.create(regionId));

        return courseStatisticsList(statistics);
    }

    public List<CourseStatisticModel> clusterCourseStats(final String clusterId) {
        notBlank(clusterId);

        final CourseStatistic statistics = courseStatisticService.statistics(Cluster.Id.create(clusterId));

        return courseStatisticsList(statistics);
    }

    public List<ClusterNameModel> clusters(final String countryId) {
        notBlank(countryId);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(countryId));

        return clusters.stream()
                       .map(cluster -> ClusterNameModel.create(cluster.getId()))
                       .sorted(comparing(ClusterNameModel::getName))
                       .collect(toList());
    }

    public List<DatedActivityStatisticModel> statsHistory(final String clusterId) {
        notBlank(clusterId);

        final List<DatedActivityStatistic> datedStats = statisticHistoryService.history(Cluster.Id.create(clusterId));

        final List<DatedActivityStatisticModel> collectedHistoryStats =
                datedStats.stream()
                          .map(stat -> DatedActivityStatisticModel.create(stat.getDate(), activityStatisticModel(stat)))
                          .collect(toList());

        collectedHistoryStats.sort(comparing(DatedActivityStatisticModel::getDate));

        return collectedHistoryStats;
    }

    public void saveStatsHistory(final String clusterId, final ActivityStatisticModel activityStatisticModel) {
        notBlank(clusterId);
        notNull(activityStatisticModel);

        statisticHistoryService.saveHistory(Cluster.Id.create(clusterId), activityStatistic(activityStatisticModel));
    }

    public void saveCourseStats(final String clusterId, final CourseStatisticsForm form) {
        notBlank(clusterId);
        notNull(form);

        clusterService.saveCourseStats(Cluster.Id.create(clusterId),
                                       CourseStatistic.create(courseStatistics(form)));
    }

    private ActivityStatisticModel activityStatisticModel(final DatedActivityStatistic stat) {
        return ActivityStatisticModel.create(activityQuantities(stat.getActivityStatistic()),
                                             coreActivities(stat.getActivityStatistic()));
    }

    private Map<String, Long> activityQuantities(final ActivityStatistic statistics) {
        final Map<String, Long> committedActivityQuantities = committedActivityQuantities(statistics);

        // Add the remainder of activities with a quantity of 0
        activityService.activities()
                       .stream()
                       .filter(activity -> !StringUtils.containsIgnoreCase(activity.getName(), PARTICIPANT))
                       .forEach(activity -> committedActivityQuantities.computeIfAbsent(activity.getName(), a -> 0L));

        // return a sorted map
        return new TreeMap<>(committedActivityQuantities);
    }
}
