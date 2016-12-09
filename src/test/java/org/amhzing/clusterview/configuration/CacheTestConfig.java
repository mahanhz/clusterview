package org.amhzing.clusterview.configuration;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.cache.CacheSpec;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.repository.*;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.infra.repository.DefaultGroupRepository;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableCaching
public class CacheTestConfig {

    @MockBean
    public CountryJpaRepository countryJpaRepository;
    @MockBean
    public RegionJpaRepository regionJpaRepository;
    @MockBean
    public ClusterJpaRepository clusterJpaRepository;
    @MockBean
    private TeamJpaRepository teamJpaRepository;
    @MockBean
    private ActivityJpaRepository activityJpaRepository;

    @Bean
    public StatisticRepository<Country.Id, ActivityStatistic> cuntryStatisticRepository() {
        return new CountryStatisticRepository(countryJpaRepository);
    }

    @Bean
    public StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository() {
        return new RegionStatisticRepository(regionJpaRepository);
    }

    @Bean
    public StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository() {
        return new ClusterStatisticRepository(clusterJpaRepository);
    }

    @Bean
    public GroupRepository groupRepository() {
        return new DefaultGroupRepository(clusterJpaRepository, teamJpaRepository, activityJpaRepository);
    }

    @Bean
    public SimpleCacheManager cacheManager(){
        final SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(ImmutableList.of(groupsCache().getObject(),
                                                statsCache().getObject()));

        return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean groupsCache(){
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.GROUPS_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean statsCache(){
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.STATS_CACHE_NAME);
        return cacheFactoryBean;
    }
}
