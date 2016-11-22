package org.amhzing.clusterview.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class ActivityTest {

    @Test
    public void getterAndSetterCorrectness() throws Exception {
        new BeanTester().testBean(Activity.class);
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Activity.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }
}