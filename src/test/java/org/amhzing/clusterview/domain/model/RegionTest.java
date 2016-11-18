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
public class RegionTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final Cluster.Id... clusters) {
        try {
            Region.create(Region.Id.create(id), ImmutableSet.copyOf(clusters));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Region.Id region1 = Region.Id.create("Central");
        final Region.Id region2 = Region.Id.create("Southern");

        final Region value = Region.create(region1, ImmutableSet.of(clusterId(), anotherClusterId()));
        final Region value2 = Region.create(region1, ImmutableSet.of(anotherClusterId(), clusterId()));
        final Region value3 = Region.create(region2, ImmutableSet.of(anotherClusterId()));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "Central", clusterId() },
                { invalidMatching(IllegalArgumentException.class), "", clusterId() },
                { invalidMatching(NullPointerException.class), null, null }
        };
    }
}