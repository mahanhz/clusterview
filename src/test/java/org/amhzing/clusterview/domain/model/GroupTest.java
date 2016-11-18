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
public class GroupTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final Location location,
                              final Member.Id... members) {
        try {
            Group.create(Group.Id.create(id), ImmutableSet.copyOf(members), location);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Group.Id group1 = Group.Id.create("Group1");
        final Group.Id group2 = Group.Id.create("Group2");

        final Group value = Group.create(group1, ImmutableSet.of(memberId(), anotherMemberId()), location());
        final Group value2 = Group.create(group1, ImmutableSet.of(anotherMemberId(), memberId()), location());
        final Group value3 = Group.create(group2, ImmutableSet.of(anotherMemberId()), location());

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "Group1", location(), memberId() },
                { invalidMatching(IllegalArgumentException.class), "", location(), memberId() },
                { invalidMatching(NullPointerException.class), "Group1", location(), null },
                { invalidMatching(NullPointerException.class), "Group1", null, memberId() }
        };
    }
}