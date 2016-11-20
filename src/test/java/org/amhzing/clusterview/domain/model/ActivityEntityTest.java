package org.amhzing.clusterview.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityEntityTest {

    @Test
    public void test_equals_hashcode() {
        final Activity activity = Activity.STUDY_CIRCLE;
        final Activity activity2 = Activity.valueOf("STUDY_CIRCLE");
        final Activity activity3 = Activity.CHILDRENS_CLASS;

        assertThat(activity).isEqualTo(activity2);
        assertThat(activity).isNotEqualTo(activity3);
    }
}