package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.cache.CacheEvicter;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;

import static javax.cache.expiry.CreatedExpiryPolicy.factoryOf;
import static org.amhzing.clusterview.cache.CacheSpec.Cache.GROUPS_CACHE;
import static org.amhzing.clusterview.cache.CacheSpec.Cache.STATS_CACHE;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            cacheManager.createCache(STATS_CACHE.getName(),
                                     new MutableConfiguration<>().setExpiryPolicyFactory(factoryOf(STATS_CACHE.getDuration()))
                                                                 .setStoreByValue(false));
            cacheManager.createCache(GROUPS_CACHE.getName(),
                                     new MutableConfiguration<>().setExpiryPolicyFactory(factoryOf(GROUPS_CACHE.getDuration()))
                                                                 .setStoreByValue(false));
        };
    }

    @Bean
    public CacheEvicter cacheEvicter() {
        return new CacheEvicter();
    }
}
