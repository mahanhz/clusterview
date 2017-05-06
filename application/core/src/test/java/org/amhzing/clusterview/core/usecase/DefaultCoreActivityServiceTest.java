package org.amhzing.clusterview.core.usecase;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.core.boundary.exit.repository.CoreActivityRepository;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.core.helper.DomainModelHelper.coreActivity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCoreActivityServiceTest {

    @Mock
    private CoreActivityRepository coreActivityRepository;

    @InjectMocks
    private DefaultCoreActivityService defaultCoreActivityService;

    @Test
    public void should_get_activities() throws Exception {

        given(coreActivityRepository.coreActivities()).willReturn(ImmutableList.of(coreActivity()));

        final List<CoreActivity> activities = defaultCoreActivityService.coreActivities();

        assertThat(activities).isNotEmpty();

        final CoreActivity activity = activities.get(0);
        assertThat(activity.getId().getId()).isEqualTo(coreActivity().getId().getId());
        assertThat(activity.getName()).isEqualTo(coreActivity().getName());
    }
}