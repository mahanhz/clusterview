package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.cache.CacheSpec;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import java.util.stream.Stream;

import static javax.cache.expiry.CreatedExpiryPolicy.factoryOf;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            Stream.of(CacheSpec.Cache.values()).forEach(cache -> {
                cacheManager.createCache(cache.getName(),
                                         new MutableConfiguration<>().setExpiryPolicyFactory(factoryOf(cache.getDuration()))
                                                                     .setStoreByValue(false));
            });
        };
    }
}
