package org.amhzing.clusterview.backend.application;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.backend.domain.model.Activity;
import org.amhzing.clusterview.backend.domain.repository.ActivityRepository;
import org.amhzing.clusterview.backend.helper.DomainModelHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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

        given(activityRepository.activities()).willReturn(ImmutableList.of(DomainModelHelper.activity()));

        final List<Activity> activities = defaultActivityService.activities();

        assertThat(activities).isNotEmpty();

        final Activity activity = activities.get(0);
        assertThat(activity.getId().getId()).isEqualTo(DomainModelHelper.activity().getId().getId());
        assertThat(activity.getName()).isEqualTo(DomainModelHelper.activity().getName());
    }
}