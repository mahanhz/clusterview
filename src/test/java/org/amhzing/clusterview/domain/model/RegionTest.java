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
                              final String name,
                              final Cluster... clusters) {
        try {
            Region.create(name, ImmutableSet.copyOf(clusters));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Region value = Region.create("Central", ImmutableSet.of(cluster(), anotherCluster()));
        final Region value2 = Region.create("Central", ImmutableSet.of(anotherCluster(), cluster()));
        final Region value3 = Region.create("Southern", ImmutableSet.of(anotherCluster()));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "Central", cluster() },
                { invalidMatching(IllegalArgumentException.class), "", cluster() },
                { invalidMatching(NullPointerException.class), null, null }
        };
    }
}