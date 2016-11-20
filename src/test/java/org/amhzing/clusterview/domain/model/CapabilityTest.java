package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.domain.model.Activity.*;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CapabilityTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final Activity... activities) {
        try {
            Capability.create(ImmutableSet.copyOf(activities));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Capability value = Capability.create(ImmutableSet.of(STUDY_CIRCLE, CHILDRENS_CLASS));
        final Capability value2 = Capability.create(ImmutableSet.of(CHILDRENS_CLASS, STUDY_CIRCLE));
        final Capability value3 = Capability.create(ImmutableSet.of(CHILDRENS_CLASS));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), STUDY_CIRCLE },
                { valid(), Activity.values() },
                { invalidMatching(NullPointerException.class), null }
        };
    }
}