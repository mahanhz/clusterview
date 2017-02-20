package org.amhzing.clusterview.backend.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class NameModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String firstName,
                              final String middleName,
                              final String lastName,
                              final String suffix) {
        try {
            NameModel.create(firstName, middleName, lastName, suffix);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(NameModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "John", "D", "Doe", "I" },
                { JUnitParamHelper.valid(), "John", null, "Doe", null },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "John", "D", "", "I" },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, "D", "Doe", "I" },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), "John", "D", null, "I" },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, "D", null, "I" }
        };
    }
}