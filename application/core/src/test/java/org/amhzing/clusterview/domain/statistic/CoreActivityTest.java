package org.amhzing.clusterview.domain.statistic;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CoreActivityTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final String name,
                              final long quantity,
                              final long total,
                              final long coi) {
        try {
            CoreActivity.create(CoreActivity.Id.create(id), name, Quantity.create(quantity), Quantity.create(total), Quantity.create(coi));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CoreActivity.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED,
                                                             Warning.STRICT_INHERITANCE,
                                                             Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "sc", "SC", 0L, 0L, 0L },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "sc", "", 0L, 0L, 0L },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, "SC", 0L, 0L, 0L }
        };
    }
}