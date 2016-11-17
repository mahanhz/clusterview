package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class NameTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final FirstName firstName,
                              final MiddleName middleName,
                              final LastName lastName,
                              final Suffix suffix) {
        try {
            Name.create(firstName, middleName, lastName, suffix);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Name value = Name.create(firstName(), middleName(), lastName(), suffix());
        final Name value2 = Name.create(firstName(), middleName(), lastName(), suffix());
        final Name value3 = Name.create(firstName(), null, lastName(), null);

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), firstName(), middleName(), lastName(), suffix() },
                { valid(), firstName(), null, lastName(), null },
                { invalidMatching(NullPointerException.class), null, middleName(), lastName(), suffix() },
                { invalidMatching(NullPointerException.class), firstName(), middleName(), null, suffix() },
                { invalidMatching(NullPointerException.class), null, middleName(), null, suffix() }
        };
    }
}