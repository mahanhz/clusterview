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
public class ActivityModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final String name) {
        try {
            ActivityModel.create(id, name);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ActivityModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), "SC", "Study Circle" },
                { invalidMatching(IllegalArgumentException.class), "", "Study Circle" },
                { invalidMatching(IllegalArgumentException.class), "dm", "" },
                { invalidMatching(NullPointerException.class), null, null }
        };
    }
}