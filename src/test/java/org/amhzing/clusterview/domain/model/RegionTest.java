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
                              final Cluster... clusters) {
        try {
            Region.create(Region.Id.create(id), ImmutableSet.copyOf(clusters));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
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