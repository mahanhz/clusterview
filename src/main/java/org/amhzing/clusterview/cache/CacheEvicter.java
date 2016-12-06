package org.amhzing.clusterview.cache;

import org.springframework.cache.annotation.CacheEvict;

import static org.amhzing.clusterview.cache.CacheSpec.GROUPS_CACHE_NAME;
import static org.amhzing.clusterview.cache.CacheSpec.STATS_CACHE_NAME;

public  class CacheEvicter {

    @CacheEvict(value = STATS_CACHE_NAME, allEntries = true)
    public void clearStatsCache() {
        // no-op
    }

    @CacheEvict(value = GROUPS_CACHE_NAME, allEntries = true)
    public void clearGroupsCache() {
        // no-op
    }
}
