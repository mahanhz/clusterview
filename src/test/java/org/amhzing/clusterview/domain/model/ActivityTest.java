package org.amhzing.clusterview.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityTest {

    @Test
    public void test_equals_hashcode() {
        final Activity.Id activity1 = Activity.Id.create("Activity1");
        final Activity.Id activity2 = Activity.Id.create("Activity1");

        final Activity value = Activity.create(activity1, "ActivityName");
        final Activity value2 = Activity.create(activity1, "ActivityName");
        final Activity value3 = Activity.create(activity2, "ActivityName");

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }
}