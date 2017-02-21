package org.amhzing.clusterview.app.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.app.helper.DomainModelHelper;
import org.amhzing.clusterview.app.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class GroupTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final long id,
                              final Location location,
                              final Member... members) {
        try {
            Group.create(Group.Id.create(id), ImmutableSet.copyOf(members), location, emptySet());
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Group.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), 123L, DomainModelHelper.location(), DomainModelHelper.member() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), 123L, DomainModelHelper.location(), null },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), 123L, null, DomainModelHelper.member() }
        };
    }
}