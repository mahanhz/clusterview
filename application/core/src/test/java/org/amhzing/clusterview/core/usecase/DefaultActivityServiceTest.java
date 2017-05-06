package org.amhzing.clusterview.core.usecase;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.core.boundary.exit.repository.ActivityRepository;
import org.amhzing.clusterview.core.domain.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.core.helper.DomainModelHelper.activity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private DefaultActivityService defaultActivityService;

    @Test
    public void should_get_activities() throws Exception {

        given(activityRepository.activities()).willReturn(ImmutableList.of(activity()));

        final List<Activity> activities = defaultActivityService.activities();

        assertThat(activities).isNotEmpty();

        final Activity activity = activities.get(0);
        assertThat(activity.getId().getId()).isEqualTo(activity().getId().getId());
        assertThat(activity.getName()).isEqualTo(activity().getName());
    }
}