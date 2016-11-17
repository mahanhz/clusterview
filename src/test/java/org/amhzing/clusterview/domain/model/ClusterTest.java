package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.anotherGroup;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ClusterTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final Group... groups) {
        try {
            Cluster.create(ImmutableSet.copyOf(groups));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Cluster value = Cluster.create(ImmutableSet.of(group(), anotherGroup()));
        final Cluster value2 = Cluster.create(ImmutableSet.of(anotherGroup(), group()));
        final Cluster value3 = Cluster.create(ImmutableSet.of(anotherGroup()));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), group() },
                { invalidMatching(NullPointerException.class), null }
        };
    }
}