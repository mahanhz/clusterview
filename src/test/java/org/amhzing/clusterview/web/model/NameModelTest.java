package org.amhzing.clusterview.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
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
    public void test_equals_hashcode() {
        final NameModel value = name();
        final NameModel value2 = name();
        final NameModel value3 = NameModel.create("Jane", null, "Doe", null);

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
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