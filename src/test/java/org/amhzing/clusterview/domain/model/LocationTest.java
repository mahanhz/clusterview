package org.amhzing.clusterview.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;


public class LocationTest {


    public void test_creation(final Class<? extends Exception> exception,
                              final int x,
                              final int y) {
        try {
            ImmutableLocation.builder().coordX(x).coordY(y).build();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }


    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Location.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), 100, 50}
        };
    }
}