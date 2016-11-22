package org.amhzing.clusterview.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.domain.model.Capability;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
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
                { valid(), "John", "D", "Doe", "I" },
                { valid(), "John", null, "Doe", null },
                { invalidMatching(IllegalArgumentException.class), "John", "D", "", "I" },
                { invalidMatching(NullPointerException.class), null, "D", "Doe", "I" },
                { invalidMatching(NullPointerException.class), "John", "D", null, "I" },
                { invalidMatching(NullPointerException.class), null, "D", null, "I" }
        };
    }

    private NameModel name() {
        return NameModel.create("John", null, "Doe", null);
    }
}