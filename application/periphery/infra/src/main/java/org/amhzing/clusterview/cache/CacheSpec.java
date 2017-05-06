package org.amhzing.clusterview.cache;

import javax.cache.expiry.Duration;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class CacheSpec {

    public static final String DEFAULT_CACHE_KEY = "#root.caches[0].name + '_' + #p0";
    //public static final String DEFAULT_CACHE_KEY = "#root.caches[0].name + '-' + #root.target + '-' + #root.method.name + '-' + #p0";

    public static final String STATS_CACHE_NAME = "statsCache";
    public static final String STATS_COURSE_CACHE_NAME = "statsCourseCache";
    public static final String STATS_HISTORY_CACHE_NAME = "statsHistoryCache";
    public static final String GROUPS_CACHE_NAME = "groupsCache";
    public static final String GROUP_CACHE_NAME = "groupCache";
    public static final String ACTIVITIES_CACHE_NAME = "activitiesCache";
    public static final String CORE_ACTIVITIES_CACHE_NAME = "coreActivitiesCache";
    public static final String REGIONS_CACHE_NAME = "regionsCache";
    public static final String CLUSTERS_CACHE_NAME = "clustersCache";

    public static enum Cache {

        STATS_CACHE(STATS_CACHE_NAME, Duration.ONE_DAY),
        STATS_COURE_CACHE(STATS_COURSE_CACHE_NAME, Duration.ONE_DAY),
        STATS_HISTORY_CACHE(STATS_HISTORY_CACHE_NAME, Duration.ONE_DAY),
        GROUPS_CACHE(GROUPS_CACHE_NAME, Duration.ONE_DAY),
        GROUP_CACHE(GROUP_CACHE_NAME, Duration.ONE_DAY),
        ACTIVITIES_CACHE(ACTIVITIES_CACHE_NAME, Duration.ONE_DAY),
        CORE_ACTIVITIES_CACHE(CORE_ACTIVITIES_CACHE_NAME, Duration.ONE_DAY),
        REGIONS_CACHE(REGIONS_CACHE_NAME, Duration.ONE_DAY),
        CLUSTERS_CACHE(CLUSTERS_CACHE_NAME, Duration.ONE_DAY);

        private final String name;
        private final Duration duration;

        Cache(final String name, final Duration duration) {
            this.name = notBlank(name);
            this.duration = notNull(duration);
        }

        public String getName() {
            return name;
        }

        public Duration getDuration() {
            return duration;
        }

    }
}
