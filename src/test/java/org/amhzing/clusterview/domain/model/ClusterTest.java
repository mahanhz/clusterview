package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ClusterTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final Group.Id... groups) {
        try {
            Cluster.create(Cluster.Id.create(id), ImmutableSet.copyOf(groups));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Cluster.Id cluster1 = Cluster.Id.create("Cluster1");
        final Cluster.Id cluster2 = Cluster.Id.create("Cluster1");

        final Cluster value = Cluster.create(cluster1, ImmutableSet.of(groupId(), anotherGroupId()));
        final Cluster value2 = Cluster.create(cluster1, ImmutableSet.of(anotherGroupId(), groupId()));
        final Cluster value3 = Cluster.create(cluster2, ImmutableSet.of(anotherGroupId()));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "Cluster1", groupId() },
                { invalidMatching(IllegalArgumentException.class), "", groupId() },
                { invalidMatching(NullPointerException.class), null, null }
        };
    }
}