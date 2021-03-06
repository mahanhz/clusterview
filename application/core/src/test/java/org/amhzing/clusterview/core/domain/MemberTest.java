package org.amhzing.clusterview.core.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.core.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.core.helper.DomainModelHelper.activity;
import static org.amhzing.clusterview.core.helper.DomainModelHelper.name;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MemberTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final long id,
                              final Name name,
                              final Capability capability,
                              final Commitment commitment) {
        try {
            Member.create(Member.Id.create(id), name, capability, commitment);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Member.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), 123L, name(), Capability.create(of(activity())), Commitment.create(of(activity())) },
                { JUnitParamHelper.valid(), 123L, name(), null, null },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), 123L, null, Capability.create(of(activity())), Commitment.create(of(activity())) }
        };
    }
}