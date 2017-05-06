package org.amhzing.clusterview.jpa.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class LocationTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Location.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }
}