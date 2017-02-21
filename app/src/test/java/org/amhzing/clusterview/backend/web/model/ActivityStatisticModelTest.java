package org.amhzing.clusterview.backend.web.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ActivityStatisticModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String key,
                              final long value) {
        try {
            ActivityStatisticModel.create(ImmutableMap.of(key, value),
                                          ImmutableList.of(CoreActivityModel.create(key, key, value, value, value)));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ActivityStatisticModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "Study Circle", 5 },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, 10 }
        };
    }
}