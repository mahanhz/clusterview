package org.amhzing.clusterview.app.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.repository.CoreActivityRepository;
import org.amhzing.clusterview.app.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.app.infra.jpa.repository.CoreActivityJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultCoreActivityRepositoryCacheTest {

    @Autowired
    private CoreActivityJpaRepository coreActivityJpaRepository;

    @Autowired
    private CoreActivityRepository coreActivityRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(coreActivityJpaRepository.findAll()).willReturn(ImmutableList.of(JpaRepositoryHelper.coreActivityEntity()));

        // First call
        final List<CoreActivity> firstCall = coreActivityRepository.coreActivities();

        // Second call
        final List<CoreActivity> secondCall = coreActivityRepository.coreActivities();

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(coreActivityJpaRepository, times(1)).findAll();
    }

    @Test
    public void should_not_invoke_cache_when_empty() throws Exception {

        given(coreActivityJpaRepository.findAll()).willReturn(emptyList());

        // First call
        final List<CoreActivity> firstCall = coreActivityRepository.coreActivities();

        // Second call
        final List<CoreActivity> secondCall = coreActivityRepository.coreActivities();

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();

        verify(coreActivityJpaRepository, times(2)).findAll();
    }
}