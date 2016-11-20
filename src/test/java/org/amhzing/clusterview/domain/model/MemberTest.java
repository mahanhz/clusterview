package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.domain.model.Activity.*;
import static org.amhzing.clusterview.helper.DomainModelHelper.anotherName;
import static org.amhzing.clusterview.helper.DomainModelHelper.name;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MemberTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
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
    public void test_equals_hashcode() {
        final Member.Id member1 = Member.Id.create("member1");
        final Member.Id member2 = Member.Id.create("member2");

        final Member value = Member.create(member1,
                                           name(),
                                           Capability.create(of(STUDY_CIRCLE, CHILDRENS_CLASS)),
                                           Commitment.create(of(HOME_VISIT, DEVOTIONAL_MEETING)));
        final Member value2 = Member.create(member1,
                                            name(),
                                            Capability.create(of(CHILDRENS_CLASS, STUDY_CIRCLE)),
                                            Commitment.create(of(DEVOTIONAL_MEETING, HOME_VISIT)));
        final Member value3 = Member.create(member2,
                                            anotherName(),
                                            Capability.create(of(CHILDRENS_CLASS, STUDY_CIRCLE)),
                                            Commitment.create(of(DEVOTIONAL_MEETING, HOME_VISIT)));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "member1", name(), Capability.create(of(STUDY_CIRCLE)), Commitment.create(of(HOME_VISIT)) },
                { valid(), "member2", name(), null, null },
                { invalidMatching(NullPointerException.class), "member1", null, Capability.create(of(STUDY_CIRCLE)), Commitment.create(of(HOME_VISIT)) }
        };
    }
}