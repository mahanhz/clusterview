package org.amhzing.clusterview.jpa.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static nl.jqno.equalsverifier.Warning.*;

public class ActivityEntityTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ActivityEntity.class).suppress(STRICT_INHERITANCE,
                                                               NONFINAL_FIELDS,
                                                               ALL_FIELDS_SHOULD_BE_USED).verify();
    }
}