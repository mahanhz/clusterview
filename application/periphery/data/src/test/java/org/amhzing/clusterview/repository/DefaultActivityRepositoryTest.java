package org.amhzing.clusterview.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.domain.Activity;
import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.repository.ActivityJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActivityRepositoryTest {

    @Mock
    private ActivityJpaRepository activityJpaRepository;

    @InjectMocks
    private DefaultActivityRepository defaultActivityRepository;

    @Test
    public void should_get_activities() throws Exception {

        given(activityJpaRepository.findAll()).willReturn(ImmutableList.of(JpaRepositoryHelper.newActivity()));

        final List<Activity> activities = defaultActivityRepository.activities();

        assertThat(activities).isNotEmpty();

        final Activity activity = activities.get(0);
        assertThat(activity.getId().getId()).isEqualTo(JpaRepositoryHelper.newActivity().getId());
        assertThat(activity.getName()).isEqualTo(JpaRepositoryHelper.newActivity().getName());
    }
}