package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.ReferenceActivityDTO;
import org.amhzing.clusterview.core.boundary.enter.ActivityService;
import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.activity;
import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.coreActivity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ActivityAdapterTest {

    @Mock
    private ActivityService activityService;
    @Mock
    private CoreActivityService coreActivityService;

    private ActivityAdapter activityAdapter;

    @Before
    public void setUp() throws Exception {
        activityAdapter = new ActivityAdapter(activityService, coreActivityService);
    }

    @Test
    public void activities() throws Exception {

        given(activityService.activities()).willReturn(ImmutableList.of(activity()));

        final ReferenceActivitiesDTO activities = activityAdapter.activities();

        assertThat(activities.activities).isNotEmpty();

        final ReferenceActivityDTO referenceActivityDTO = activities.activities.get(0);
        assertThat(referenceActivityDTO.id).isEqualTo(activity().getId().getId());
        assertThat(referenceActivityDTO.name).isEqualTo(activity().getName());
    }

    @Test
    public void coreActivities() throws Exception {
        given(coreActivityService.coreActivities()).willReturn(ImmutableList.of(coreActivity()));

        final ReferenceActivitiesDTO activities = activityAdapter.coreActivities();

        assertThat(activities.activities).isNotEmpty();

        final ReferenceActivityDTO referenceActivityDTO = activities.activities.get(0);
        assertThat(referenceActivityDTO.id).isEqualTo(coreActivity().getId().getId());
        assertThat(referenceActivityDTO.name).isEqualTo(coreActivity().getName());
    }

}