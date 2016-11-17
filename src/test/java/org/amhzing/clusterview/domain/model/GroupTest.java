package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.anotherMember;
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
                              final Location location,
                              final Member... members) {
        try {
            Group.create(ImmutableSet.copyOf(members), location);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Group value = Group.create(ImmutableSet.of(member(), anotherMember()), location());
        final Group value2 = Group.create(ImmutableSet.of(anotherMember(), member()), location());
        final Group value3 = Group.create(ImmutableSet.of(anotherMember()), location());

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), location(), member() },
                { invalidMatching(NullPointerException.class), location(), null },
                { invalidMatching(NullPointerException.class), null, member() }
        };
    }
}