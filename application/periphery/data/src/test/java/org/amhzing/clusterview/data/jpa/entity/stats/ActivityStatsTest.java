package org.amhzing.clusterview.data.jpa.entity.stats;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ActivityStatsTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ActivityStats.class).suppress(Warning.STRICT_INHERITANCE,
                                                               Warning.NONFINAL_FIELDS).verify();
    }
}