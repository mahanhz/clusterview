package org.amhzing.clusterview.app.domain.model.statistic;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.junit.Assert.fail;

public class CourseStatisticTest {

    @Test(expected = NullPointerException.class)
    public void should_not_accept_null() {
        CourseStatistic.create(null);

        fail("This test should have failed with an exception by now");
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CourseStatistic.class).suppress(Warning.STRICT_INHERITANCE,
                                                                  Warning.NONFINAL_FIELDS).verify();
    }
}