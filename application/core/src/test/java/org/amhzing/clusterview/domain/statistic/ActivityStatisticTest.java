package org.amhzing.clusterview.domain.statistic;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityStatisticTest {

    @Test
    public void should_not_return_null() {
        final ActivityStatistic activityStatistic = new ActivityStatistic();

        Assertions.assertThat(activityStatistic.getActivityQuantity()).isNotNull();
        assertThat(activityStatistic.getCoreActivities()).isNotNull();
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ActivityStatistic.class).suppress(Warning.STRICT_INHERITANCE,
                                                                  Warning.NONFINAL_FIELDS).verify();
    }
}