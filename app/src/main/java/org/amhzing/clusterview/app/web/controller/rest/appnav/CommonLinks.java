package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.ACTIVITY_STATS;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.COURSE_STATS;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public final class CommonLinks {

    public static final String REL_HOME = "home";
    public static final String REL_STATS_ACTIVITY = "stats-activity";
    public static final String REL_STATS_COURSE = "stats-course";
    public static final String REL_STATS_HISTORY = "stats-history";
    public static final String REL_STATS_SAVE_HISTORY = "stats-save-history";
    public static final String REL_COUNTRY = "country";
    public static final String REL_REF_DATA_ACTIVITIES = "reference-data-activities";
    public static final String REL_REF_DATA_CORE_ACTIVITIES = "reference-data-core-activities";
    public static final String REL_REF_DATA_CLUSTERS = "reference-data-clusters";

    public static final String REGION_PREFIX = "region-";
    public static final String CLUSTER_PREFIX = "cluster-";
    public static final String GROUP_PREFIX = "group-";
    public static final String CLUSTER_STATS_HISTORY_PREFIX = REL_STATS_HISTORY + "-";

    private CommonLinks() {
        // Prevent instantiation
    }

    public static Link homeLink() {
        final ControllerLinkBuilder homeLink = linkTo(IndexRestController.class);
        return homeLink.withRel(REL_HOME);
    }

    public static Link activitiesRefDataLink() {
        final ControllerLinkBuilder link = linkTo(methodOn(ReferenceDataRestController.class).activities());
        return link.withRel(REL_REF_DATA_ACTIVITIES);
    }

    public static Link coreActivitiesRefDataLink() {
        final ControllerLinkBuilder link = linkTo(methodOn(ReferenceDataRestController.class).coreActivities());
        return link.withRel(REL_REF_DATA_CORE_ACTIVITIES);
    }

    public static Link clustersRefDataLink() {
        final ControllerLinkBuilder link = linkTo(ReferenceDataRestController.class).slash("clusters");
        return link.withRel(REL_REF_DATA_CLUSTERS);
    }

    public static Link countryLink(final String country) {
        notBlank(country);

        final ControllerLinkBuilder countryLink = linkTo(methodOn(CountryRestController.class).country(country));
        return countryLink.withRel(REL_COUNTRY);
    }

    public static Link regionLink(final String country, final String region) {
        notBlank(country);
        notBlank(region);

        final ControllerLinkBuilder regionLink = linkTo(RegionRestController.class).slash(country).slash(region);
        return regionLink.withRel(REGION_PREFIX + region);
    }

    public static Link clusterLink(final String country, final String region, final String cluster) {
        notBlank(country);
        notBlank(region);
        notBlank(cluster);

        final ControllerLinkBuilder regionLink = linkTo(GroupRestController.class).slash(country)
                                                                                   .slash(region)
                                                                                   .slash(cluster);
        return regionLink.withRel(CLUSTER_PREFIX + cluster);
    }

    public static Link clusterActivityStatsLink(final String country, final String region, final String cluster) {
        notBlank(country);
        notBlank(region);
        notBlank(cluster);

        final ControllerLinkBuilder activityStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(cluster).slash(ACTIVITY_STATS);
        return activityStatsLink.withRel(REL_STATS_ACTIVITY);
    }

    public static Link clusterCourseStatsLink(final String country, final String region, final String cluster) {
        notBlank(country);
        notBlank(region);
        notBlank(cluster);

        final ControllerLinkBuilder courseStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(cluster).slash(COURSE_STATS);
        return courseStatsLink.withRel(REL_STATS_COURSE);
    }

    public static Link statsHistoryLink(final String country) {
        notBlank(country);

        final ControllerLinkBuilder statsHistoryLink = linkTo(methodOn(StatisticRestController.class).history(country));
        return statsHistoryLink.withRel(REL_STATS_HISTORY);
    }
}
