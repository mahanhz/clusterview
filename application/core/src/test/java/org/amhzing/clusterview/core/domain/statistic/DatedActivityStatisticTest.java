package org.amhzing.clusterview.core.domain.statistic;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class DatedActivityStatisticTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(DatedActivityStatistic.class).suppress(Warning.STRICT_INHERITANCE,
                                                                            Warning.NONFINAL_FIELDS).verify();
    }
}