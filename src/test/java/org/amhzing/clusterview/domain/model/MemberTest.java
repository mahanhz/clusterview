package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
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

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), 123L, name(), Capability.create(of(activity())), Commitment.create(of(activity())) },
                { valid(), 123L, name(), null, null },
                { invalidMatching(NullPointerException.class), 123L, null, Capability.create(of(activity())), Commitment.create(of(activity())) }
        };
    }
}