package org.amhzing.clusterview.app.web.adapter;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.web.model.ActivityModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.activity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActivityAdapterTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityAdapter activityAdapter;

    @Test
    public void should_get_activities() throws Exception {

        given(activityService.activities()).willReturn(ImmutableList.of(activity()));

        final List<ActivityModel> activities = activityAdapter.activities();

        assertThat(activities).isNotEmpty();

        final ActivityModel activityModel = activities.get(0);
        assertThat(activityModel.getId()).isEqualTo(activity().getId().getId());
        assertThat(activityModel.getName()).isEqualTo(activity().getName());
    }
}