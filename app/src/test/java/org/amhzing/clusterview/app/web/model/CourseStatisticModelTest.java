package org.amhzing.clusterview.app.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.app.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.app.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CourseStatisticModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final String name,
                              final int quantity) {
        try {
            CourseStatisticModel.create(id, name, quantity);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CourseStatisticModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "1", "Book 1", 5 },
                { invalidMatching(IllegalArgumentException.class), "", "Book 1", 5 },
                { invalidMatching(IllegalArgumentException.class), "1", "", 5 },
                { invalidMatching(NullPointerException.class), null, "Book 1", 5 },
                { invalidMatching(NullPointerException.class), "1", null, 5 }
        };
    }
}