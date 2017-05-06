package org.amhzing.clusterview.testconfig;

import org.junit.rules.ExternalResource;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.apache.commons.lang3.Validate.notNull;

public class CacheInvalidateRule extends ExternalResource {

    private final CacheManager cacheManager;

    public CacheInvalidateRule(final CacheManager cacheManager) {
        this.cacheManager = notNull(cacheManager);
    }

    @Override
    protected void before() throws Throwable {
        cacheManager.getCacheNames().stream()
                    .map(cacheManager::getCache)
                    .forEach(Cache::clear);
    }
}
