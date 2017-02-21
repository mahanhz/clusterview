package org.amhzing.clusterview.backend.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.backend.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ClusterTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final Group... groups) {
        try {
            Cluster.create(Cluster.Id.create(id), ImmutableSet.copyOf(groups));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Cluster.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "Cluster1", group() },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "", group() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, null }
        };
    }
}