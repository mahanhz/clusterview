package org.amhzing.clusterview.backend.infra.jpa.mapping;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.backend.domain.model.statistic.Quantity;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ParticipantQuantityTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final long quantity,
                              final long total,
                              final long coi) {
        try {
            ParticipantQuantity.create(quantity, total, coi);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Quantity.class).suppress(Warning.STRICT_INHERITANCE,
                                                         Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), 0L, 0L, 0L },
                { JUnitParamHelper.valid(), 3L, 10L, 5L }
        };
    }
}