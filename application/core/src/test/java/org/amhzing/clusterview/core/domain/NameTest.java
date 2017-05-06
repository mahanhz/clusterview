package org.amhzing.clusterview.core.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.core.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.core.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.core.helper.JUnitParamHelper.valid;
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
            ImmutableName.builder()
                         .firstName(firstName)
                         .middleName(middleName)
                         .lastName(lastName)
                         .suffix(suffix)
                         .build();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
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