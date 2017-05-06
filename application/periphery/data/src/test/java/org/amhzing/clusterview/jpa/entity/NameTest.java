package org.amhzing.clusterview.jpa.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class NameTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Name.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }
}