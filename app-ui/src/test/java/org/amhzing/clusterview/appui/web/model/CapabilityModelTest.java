package org.amhzing.clusterview.appui.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.appui.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.ImmutableList.copyOf;
import static org.amhzing.clusterview.appui.helper.ClientModelHelper.activityModel;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CapabilityModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final ActivityModel... activities) {
        try {
            CapabilityModel.create(copyOf(activities));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CapabilityModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), activityModel() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null }
        };
    }
}