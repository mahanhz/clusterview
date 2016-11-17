package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class NameTest {

    @Test
    @Parameters(method = "values")
    public void name_is_valid(final Class<? extends Exception> exception,
                              final FirstName firstName,
                              final MiddleName middleName,
                              final LastName lastName,
                              final Suffix suffix)  {
        try {
            Name.create(firstName, middleName, lastName, suffix);
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

    private FirstName firstName() {
        return FirstName.create("firstName");
    }

    private MiddleName middleName() {
        return MiddleName.create("middleName");
    }

    private LastName lastName() {
        return LastName.create("lastName");
    }

    private Suffix suffix() {
        return Suffix.create("suffix");
    }
}