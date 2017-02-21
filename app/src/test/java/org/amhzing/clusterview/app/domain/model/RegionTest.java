package org.amhzing.clusterview.app.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.app.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.app.helper.JUnitParamHelper.valid;
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

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Region.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
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