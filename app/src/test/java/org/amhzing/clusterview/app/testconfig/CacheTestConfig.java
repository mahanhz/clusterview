package org.amhzing.clusterview.app.testconfig;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.cache.CacheSpec;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.*;
import org.amhzing.clusterview.app.infra.jpa.repository.*;
import org.amhzing.clusterview.app.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.app.infra.repository.*;
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
    @MockBean
    private StatsHistoryJpaRepository statsHistoryJpaRepository;
    @MockBean
    private CoreActivityJpaRepository coreActivityJpaRepository;
    @MockBean
    private CourseJpaRepository courseJpaRepository;

    @Bean
    public StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository() {
        return new CountryStatisticRepository(countryJpaRepository, groupRepository());
    }

    @Bean
    public StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository() {
        return new RegionStatisticRepository(regionJpaRepository, groupRepository());
    }

    @Bean
    public StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository() {
        return new ClusterStatisticRepository(clusterJpaRepository, groupRepository());
    }

    @Bean
    public StatisticRepository<Country.Id, CourseStatistic> countryCourseStatisticRepository() {
        return new CountryCourseStatisticRepository(countryJpaRepository);
    }

    @Bean
    public StatisticRepository<Region.Id, CourseStatistic> regionCourseStatisticRepository() {
        return new RegionCourseStatisticRepository(regionJpaRepository);
    }

    @Bean
    public StatisticRepository<Cluster.Id, CourseStatistic> clusterCourseStatisticRepository() {
        return new ClusterCourseStatisticRepository(clusterJpaRepository, courseJpaRepository);
    }

    @Bean
    public StatisticHistoryRepository statisticHistoryRepository() {
        return new DefaultStatisticHistoryRepository(statsHistoryJpaRepository);
    }

    @Bean
    public ClusterRepository clusterRepository() {
        return new DefaultClusterRepository(countryJpaRepository, clusterJpaRepository);
    }

    @Bean
    public ActivityRepository activityRepository() {
        return new DefaultActivityRepository(activityJpaRepository);
    }

    @Bean
    public CoreActivityRepository coreActivityRepository() {
        return new DefaultCoreActivityRepository(coreActivityJpaRepository);
    }

    @Bean
    public GroupRepository groupRepository() {
        return new DefaultGroupRepository(clusterJpaRepository, teamJpaRepository, activityJpaRepository);
    }

    @Bean
    public SimpleCacheManager cacheManager(){
        final SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(ImmutableList.of(groupsCache().getObject(),
                                                groupCache().getObject(),
                                                statsCache().getObject(),
                                                statsCourseCache().getObject(),
                                                statsHistoryCache().getObject(),
                                                activitiesCache().getObject(),
                                                coreActivitiesCache().getObject(),
                                                clustersCache().getObject()));

        return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean groupsCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.GROUPS_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean statsCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.STATS_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean statsCourseCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.STATS_COURSE_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean statsHistoryCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.STATS_HISTORY_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean activitiesCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.ACTIVITIES_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean coreActivitiesCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.CORE_ACTIVITIES_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean clustersCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.CLUSTERS_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean groupCache() {
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName(CacheSpec.GROUP_CACHE_NAME);
        return cacheFactoryBean;
    }

    @Bean
    public CacheInvalidateRule cacheInvalidationRule() {
        return new CacheInvalidateRule(cacheManager());
    }
}
