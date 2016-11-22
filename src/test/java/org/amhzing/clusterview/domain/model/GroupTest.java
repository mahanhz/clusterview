package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.BeanTester;

import static org.amhzing.clusterview.helper.DomainModelHelper.location;
import static org.amhzing.clusterview.helper.DomainModelHelper.member;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
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
            Group.create(Group.Id.create(id), ImmutableSet.copyOf(members), location);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void getterAndSetterCorrectness() throws Exception {
        new BeanTester().testBean(Group.class);
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Group.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), 123L, location(), member() },
                { invalidMatching(NullPointerException.class), 123L, location(), null },
                { invalidMatching(NullPointerException.class), 123L, null, member() }
        };
    }
}