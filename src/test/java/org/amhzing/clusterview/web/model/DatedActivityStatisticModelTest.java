package org.amhzing.clusterview.web.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class DatedActivityStatisticModelTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(DatedActivityStatisticModel.class).suppress(Warning.STRICT_INHERITANCE,
                                                                            Warning.NONFINAL_FIELDS).verify();
    }
}