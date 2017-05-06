package org.amhzing.clusterview.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.domain.statistic.CoreActivity;
import org.amhzing.clusterview.jpa.repository.CoreActivityJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.coreActivityEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCoreActivityRepositoryTest {

    @Mock
    private CoreActivityJpaRepository coreActivityJpaRepository;

    @InjectMocks
    private DefaultCoreActivityRepository defaultCoreActivityRepository;

    @Test
    public void should_get_activities() throws Exception {

        given(coreActivityJpaRepository.findAll()).willReturn(ImmutableList.of(coreActivityEntity()));

        final List<CoreActivity> activities = defaultCoreActivityRepository.coreActivities();

        assertThat(activities).isNotEmpty();

        final CoreActivity activity = activities.get(0);
        assertThat(activity.getId().getId()).isEqualTo(coreActivityEntity().getId());
        assertThat(activity.getName()).isEqualTo(coreActivityEntity().getName());
    }
}