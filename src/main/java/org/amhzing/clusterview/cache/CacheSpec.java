package org.amhzing.clusterview.cache;

import javax.cache.expiry.Duration;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class CacheSpec {

    public static final String DEFAULT_CACHE_KEY = "#root.caches[0].name + '-' + #root.target + '-' + #root.method.name + '-' + #p0";
    public static final String STATS_CACHE_NAME = "statsCache";
    public static final String GROUPS_CACHE_NAME = "groupsCache";

    public static enum Cache {

        STATS_CACHE(STATS_CACHE_NAME, Duration.ONE_DAY),
        GROUPS_CACHE(GROUPS_CACHE_NAME, Duration.ONE_DAY);

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
