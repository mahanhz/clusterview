package org.amhzing.clusterview.app.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.Activity;
import org.amhzing.clusterview.app.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.app.infra.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
import org.amhzing.clusterview.app.domain.repository.ActivityRepository;
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
public class DefaultActivityRepositoryCacheTest {

    @Autowired
    private ActivityJpaRepository activityJpaRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(activityJpaRepository.findAll()).willReturn(ImmutableList.of(JpaRepositoryHelper.newActivity()));

        // First call
        final List<Activity> firstCall = activityRepository.activities();

        // Second call
        final List<Activity> secondCall = activityRepository.activities();

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(activityJpaRepository, times(1)).findAll();
    }

    @Test
    public void should_not_invoke_cache_when_empty() throws Exception {

        given(activityJpaRepository.findAll()).willReturn(emptyList());

        // First call
        final List<Activity> firstCall = activityRepository.activities();

        // Second call
        final List<Activity> secondCall = activityRepository.activities();

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();

        verify(activityJpaRepository, times(2)).findAll();
    }
}