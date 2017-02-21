package org.amhzing.clusterview.app.web.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class CoreActivityModelTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CoreActivityModel.class).suppress(Warning.STRICT_INHERITANCE,
                                                               Warning.NONFINAL_FIELDS).verify();
    }
}