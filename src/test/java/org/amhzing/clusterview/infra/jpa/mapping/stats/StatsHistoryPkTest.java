package org.amhzing.clusterview.infra.jpa.mapping.stats;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class StatsHistoryPkTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(StatsHistoryPk.class).suppress(Warning.STRICT_INHERITANCE,
                                                                   Warning.NONFINAL_FIELDS).verify();
    }
}