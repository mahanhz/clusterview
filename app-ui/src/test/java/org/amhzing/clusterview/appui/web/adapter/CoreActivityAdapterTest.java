package org.amhzing.clusterview.appui.web.adapter;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.application.CoreActivityService;
import org.amhzing.clusterview.appui.web.model.CoreActivityModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.appui.helper.DomainModelHelper.coreActivity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CoreActivityAdapterTest {

    @Mock
    private CoreActivityService coreActivityService;

    @InjectMocks
    private CoreActivityAdapter coreActivityAdapter;

    @Test
    public void should_get_activities() throws Exception {

        given(coreActivityService.coreActivities()).willReturn(ImmutableList.of(coreActivity()));

        final List<CoreActivityModel> activities = coreActivityAdapter.coreActivities();

        assertThat(activities).isNotEmpty();

        final CoreActivityModel activityModel = activities.get(0);
        assertThat(activityModel.getId()).isEqualTo(coreActivity().getId().getId());
        assertThat(activityModel.getName()).isEqualTo(coreActivity().getName());
    }
}